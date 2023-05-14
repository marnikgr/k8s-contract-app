package com.uniwa.contract.application.service;

import com.uniwa.contract.application.entity.ApplicantEntity;
import com.uniwa.contract.application.entity.ApplicationEntity;
import com.uniwa.contract.application.repository.ApplicantRepository;
import com.uniwa.contract.application.repository.ApplicationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ApplicationPersistenceService {

    private ApplicationRepository applicationRepository;

    private ApplicantRepository applicantRepository;

    public Optional<ApplicantEntity> findApplicant(String username) {

        return applicantRepository.findByEmail(username);
    }

    public ApplicantEntity saveApplicant(ApplicantEntity applicant) {

        return applicantRepository.save(applicant);
    }

    public ApplicationEntity saveApplication(ApplicationEntity application) {

        return applicationRepository.save(application);
    }

    public List<ApplicationEntity> findApplicationsByUsername(String username) {

        List<ApplicationEntity> applicationList = new ArrayList<>();
        Optional<ApplicantEntity> applicant = applicantRepository.findByEmail(username);
        if (applicant.isPresent()) {
            applicationList = applicationRepository.findAllByApplicantOrderBySubmissionDateDesc(applicant.get());
        }
        return applicationList;
    }

    public Optional<ApplicationEntity> findApplicationById(Long applicationId) {

        return applicationRepository.findById(applicationId);
    }
}
