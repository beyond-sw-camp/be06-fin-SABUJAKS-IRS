package com.service.verification.repository;


import com.service.verification.entity.CompanyVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CompanyVerificationRepository  extends JpaRepository<CompanyVerification, Long> {

    @Query("SELECT cv FROM CompanyVerification cv WHERE cv.recruiterEmail = :recruiterEmail")
    Optional<CompanyVerification> findByRecruiterEmail(String recruiterEmail);

    @Modifying
    @Query("DELETE FROM CompanyVerification cv WHERE cv.recruiterEmail = :recruiterEmail")
    void deleteByRecruiterEmail(String recruiterEmail);
}
