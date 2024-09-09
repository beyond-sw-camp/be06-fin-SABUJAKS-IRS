package com.sabujaks.irs.domain.auth.repository;

import com.sabujaks.irs.domain.auth.model.entity.EmailVerify;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerifyRepository extends JpaRepository<EmailVerify, Long> {
    Optional<EmailVerify> findByEmail(String email);
}
