package com.example.common.domain.company.repository;


import com.example.common.domain.company.model.entity.CompanyImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyImgRepository extends JpaRepository<CompanyImg, Long> {
    List<CompanyImg> findByCompanyIdx(Long idx);
}