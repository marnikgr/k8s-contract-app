package com.uniwa.contract.notification.service;

import com.uniwa.contract.notification.enumeration.KafkaTopicEnum;
import com.uniwa.contract.notification.model.EmailInfo;
import com.uniwa.contract.notification.model.KafkaMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class NotificationService {

    private final EmailService emailService;

    public void notifyApplicant(String topic, KafkaMessage kafkaMessage) {

        EmailInfo emailInfo = EmailInfo.builder()
                .email(kafkaMessage.getApplicantEmail())
                .subject("Application Email Notification")
                .values(populateValues(topic, kafkaMessage))
                .build();
        emailService.send(emailInfo);
    }

    private Map<String, Object> populateValues(String topic, KafkaMessage kafkaMessage) {

        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put("applicationId", kafkaMessage.getApplicationId());
        valuesMap.put("applicantFullName", kafkaMessage.getApplicantFullName());
        valuesMap.put("applicationStatus", populateApplicationStatus(topic));
        return valuesMap;
    }

    private String populateApplicationStatus(String topic) {

        String applicationStatus = null;
        KafkaTopicEnum kafkaTopicEnum = KafkaTopicEnum.fromValue(topic);
        if (kafkaTopicEnum != null) {
            switch (kafkaTopicEnum) {
                case SUBMITTED -> applicationStatus = KafkaTopicEnum.SUBMITTED.name();
                case PROCESSED -> applicationStatus = KafkaTopicEnum.PROCESSED.name();
                case COMPLETED -> applicationStatus = KafkaTopicEnum.COMPLETED.name();
                case CANCELED -> applicationStatus = KafkaTopicEnum.CANCELED.name();
            }
        }
        return applicationStatus;
    }
}
