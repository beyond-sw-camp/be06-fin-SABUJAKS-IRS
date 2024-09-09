package com.sabujaks.irs.domain.resume.repository;

import com.sabujaks.irs.domain.resume.model.entity.ResumeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeInfoRepository extends JpaRepository<ResumeInfo, Long> {
}
