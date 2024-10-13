package com.example.common.domain.auth.repository;

import com.example.common.domain.auth.model.entity.CompanyVerify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface CompanyVerifyRepository  extends JpaRepository<CompanyVerify, Long> {
    @Query("SELECT cv FROM CompanyVerify cv WHERE cv.recruiterEmail = :recruiterEmail")
    Optional<CompanyVerify> findByRecruiterEmail(String recruiterEmail);
}
