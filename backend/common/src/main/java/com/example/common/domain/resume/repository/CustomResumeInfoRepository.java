package com.example.common.domain.resume.repository;

import com.example.common.domain.resume.model.entity.CustomResumeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CustomResumeInfoRepository extends JpaRepository<CustomResumeInfo, Integer> {
    @Query("SELECT cri FROM CustomResumeInfo cri WHERE cri.resumeInfo.idx = :resumeInfoIdx")
    List<CustomResumeInfo> findAllByResumeInfoIdx(Long resumeInfoIdx);
}
