package com.service.resume.repository;


import com.service.resume.entity.StudyingAbroad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudyingAboardRepository extends JpaRepository<StudyingAbroad, Long> {
    @Query("SELECT sa FROM StudyingAbroad sa WHERE sa.resume.idx = :resumeIdx")
    List<StudyingAbroad> findAllByResumeIdx(Long resumeIdx);
}
