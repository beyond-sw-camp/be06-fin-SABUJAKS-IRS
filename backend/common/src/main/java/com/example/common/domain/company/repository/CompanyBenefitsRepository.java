package com.example.common.domain.company.repository;


import com.example.common.domain.company.model.entity.CompanyBenefits;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyBenefitsRepository extends JpaRepository<CompanyBenefits, Integer> {
    List<CompanyBenefits> findAllByCompanyIdx(Long companyIdx);
}