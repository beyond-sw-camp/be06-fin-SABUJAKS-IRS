package com.service.verification.repository;

import com.service.verification.entity.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {

    @Query("SELECT ev From EmailVerification ev WHERE ev.email = :email")
    Optional<EmailVerification> findByEmail(String email);

    @Modifying
    @Query("DELETE FROM EmailVerification ev WHERE ev.email = :email")
    void deleteByEmail(String email);
}
