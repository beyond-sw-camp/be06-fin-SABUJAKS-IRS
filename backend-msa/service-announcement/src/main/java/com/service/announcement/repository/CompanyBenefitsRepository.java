package com.service.announcement.repository;

import com.service.announcement.entity.CompanyBenefits;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyBenefitsRepository extends JpaRepository<CompanyBenefits, Integer> {
    List<CompanyBenefits> findAllByCompanyIdx(Long companyIdx);
}