package com.sabujaks.irs.domain.company.repository;

import com.sabujaks.irs.domain.company.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
