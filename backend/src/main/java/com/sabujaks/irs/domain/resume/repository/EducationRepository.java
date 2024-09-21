package com.sabujaks.irs.domain.resume.repository;

import com.sabujaks.irs.domain.resume.model.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Long> {
    @Query("SELECT e FROM Education e WHERE e.resumeInfo.idx = :resumeInfoIdx")
    List<Education> findAllByResumeInfoIdx(Long resumeInfoIdx);
}
