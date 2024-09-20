package com.sabujaks.irs.domain.company.repository;

import com.sabujaks.irs.domain.company.model.entity.CompanyBenefits;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyBenefitsRepository extends JpaRepository<CompanyBenefits, Integer> {
    List<CompanyBenefits> findAllByCompanyIdx(Long companyIdx);
}