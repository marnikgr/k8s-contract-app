package com.uniwa.contract.application.repository;

import com.uniwa.contract.application.entity.ApplicantEntity;
import com.uniwa.contract.application.entity.ApplicationEntity;
import com.uniwa.contract.application.enumeration.ApplicationStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {

    List<ApplicationEntity> findAllByStatus(ApplicationStatusEnum status);

    List<ApplicationEntity> findAllByApplicantOrderBySubmissionDateDesc(ApplicantEntity applicant);


}
