package com.service.resume.repository;

import com.service.resume.entity.CustomResume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomResumeRepository extends JpaRepository<CustomResume, Integer> {
    @Query("SELECT cri FROM CustomResume cri WHERE cri.resume.idx = :resumeIdx")
    List<CustomResume> findAllByResumeIdx(Long resumeIdx);
}
