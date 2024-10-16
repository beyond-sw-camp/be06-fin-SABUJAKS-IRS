package com.service.resume.repository;

import com.service.resume.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Long> {
    @Query("SELECT e FROM Education e WHERE e.resume.idx = :resumeIdx")
    List<Education> findAllByResumeIdx(Long resumeIdx);
}
