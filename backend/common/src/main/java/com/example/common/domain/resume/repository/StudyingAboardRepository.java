package com.example.common.domain.resume.repository;

import com.example.common.domain.resume.model.entity.StudyingAbroad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface StudyingAboardRepository extends JpaRepository<StudyingAbroad, Long> {
    @Query("SELECT sa FROM StudyingAbroad sa WHERE sa.resumeInfo.idx = :resumeInfoIdx")
    List<StudyingAbroad> findAllByResumeInfoIdx(Long resumeInfoIdx);
}
