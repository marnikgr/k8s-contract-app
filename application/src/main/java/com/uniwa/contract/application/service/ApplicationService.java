package com.uniwa.contract.application.service;

import com.uniwa.contract.application.client.AccountClient;
import com.uniwa.contract.application.entity.ApplicantEntity;
import com.uniwa.contract.application.entity.ApplicationEntity;
import com.uniwa.contract.application.enumeration.ApplicationStatusEnum;
import com.uniwa.contract.application.enumeration.KafkaTopicEnum;
import com.uniwa.contract.application.exception.ApplicationNotFoundException;
import com.uniwa.contract.application.model.AccountDetails;
import com.uniwa.contract.application.model.KafkaMessage;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ApplicationService {

    private final ApplicationPersistenceService applicationPersistenceService;

    private final AccountClient accountClient;

    private final KafkaProducerService kafkaProducerService;

    public ApplicationEntity create(String username) {

        ApplicantEntity applicantEntity = applicationPersistenceService.findApplicant(username)
                .orElseGet(() -> persistApplicant(username));

        LocalDateTime now = LocalDateTime.now();
        ApplicationEntity applicationEntity = ApplicationEntity.builder()
                .applicant(applicantEntity)
                .status(ApplicationStatusEnum.SUBMITTED)
                .submissionDate(now)
                .lastUpdateDate(now)
                .build();

        ApplicationEntity application = applicationPersistenceService.saveApplication(applicationEntity);
        sendNotification(application);
        return application;
    }

    public List<ApplicationEntity> retrieveByUsername(String username) {

        return applicationPersistenceService.findApplicationsByUsername(username);
    }

    public ApplicationEntity retrieve(Long applicationId) {

        return applicationPersistenceService.findApplicationById(applicationId).orElse(null);
    }

    @SneakyThrows
    public void update(Long applicationId, ApplicationStatusEnum status) {

        ApplicationEntity applicationEntity = applicationPersistenceService.findApplicationById(applicationId)
                .orElseThrow(()
                        -> new ApplicationNotFoundException("Application with Id: {} cannot be found" + applicationId));
        applicationEntity.setStatus(status);
        applicationEntity.setLastUpdateDate(LocalDateTime.now());
        applicationPersistenceService.saveApplication(applicationEntity);
        sendNotification(applicationEntity);

    }

    @SneakyThrows
    public void delete(Long applicationId) {

        ApplicationEntity applicationEntity = applicationPersistenceService.findApplicationById(applicationId)
                .orElseThrow(()
                        -> new ApplicationNotFoundException("Application with Id: {} cannot be found" + applicationId));
        applicationEntity.setStatus(ApplicationStatusEnum.CANCELED);
        applicationEntity.setLastUpdateDate(LocalDateTime.now());
        applicationPersistenceService.saveApplication(applicationEntity);
        sendNotification(applicationEntity);
    }

    private ApplicantEntity persistApplicant(String username) {

        AccountDetails accountDetails = accountClient.accountDetails(username).getBody();
        ApplicantEntity applicant = ApplicantEntity.builder()
                .userId(accountDetails.getUserId())
                .fullName(accountDetails.getFullName())
                .lastName(accountDetails.getLastName())
                .givenName(accountDetails.getGivenName())
                .displayName(accountDetails.getDisplayName())
                .email(accountDetails.getEmail())
                .build();
        return applicationPersistenceService.saveApplicant(applicant);
    }

    private void sendNotification(ApplicationEntity applicationEntity) {

        KafkaTopicEnum topicEnum;
        switch (applicationEntity.getStatus()) {
            case SUBMITTED -> topicEnum = KafkaTopicEnum.SUBMITTED;
            case PROCESSED -> topicEnum = KafkaTopicEnum.PROCESSED;
            case COMPLETED -> topicEnum = KafkaTopicEnum.COMPLETED;
            case CANCELED -> topicEnum = KafkaTopicEnum.CANCELED;
            default -> {
                return;
            }
        }
        KafkaMessage message = KafkaMessage.builder()
                .applicationId(applicationEntity.getApplicationId())
                .applicantEmail(applicationEntity.getApplicant().getEmail())
                .applicantFullName(applicationEntity.getApplicant().getFullName())
                .build();
        kafkaProducerService.produceMessage(message, topicEnum);
    }


}
