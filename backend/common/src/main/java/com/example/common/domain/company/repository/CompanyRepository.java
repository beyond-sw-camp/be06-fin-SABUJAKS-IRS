package com.example.common.domain.company.repository;

import com.example.common.domain.company.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("SELECT c FROM Company c WHERE c.recruiter.idx = :recruiterIdx")
    Optional<Company> findByRecruiterIdx(Long recruiterIdx);
}