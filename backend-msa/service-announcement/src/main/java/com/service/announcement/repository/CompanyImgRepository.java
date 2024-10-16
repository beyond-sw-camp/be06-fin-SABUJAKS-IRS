package com.service.announcement.repository;

import com.service.announcement.entity.CompanyImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyImgRepository extends JpaRepository<CompanyImg, Long> {
    List<CompanyImg> findByCompanyIdx(Long idx);
}