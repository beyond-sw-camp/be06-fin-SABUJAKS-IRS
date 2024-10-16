package com.service.announcement.repository;

import com.service.announcement.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("SELECT c FROM Company c WHERE c.recruiterIdx = :recruiterIdx")
    Optional<Company> findByRecruiterIdx(Long recruiterIdx);
}