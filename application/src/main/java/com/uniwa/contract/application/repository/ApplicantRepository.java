package com.uniwa.contract.application.repository;

import com.uniwa.contract.application.entity.ApplicantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicantRepository extends JpaRepository<ApplicantEntity, Long> {

    Optional<ApplicantEntity> findByEmail(String username);
}
