package com.sabujaks.irs.domain.resume.repository;

import com.sabujaks.irs.domain.resume.model.entity.CustomResumeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomResumeInfoRepository extends JpaRepository<CustomResumeInfo, Integer> {
}
