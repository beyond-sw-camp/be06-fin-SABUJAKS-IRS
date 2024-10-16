package com.service.resume.repository;


import com.service.resume.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    @Query("SELECT r FROM Resume r WHERE r.seekerIdx = :seekerIdx AND r.integrated = :integration")
    Optional<Resume> findBySeekerIdxAndIntegrated(Long seekerIdx, Boolean integration);

    @Query("SELECT r FROM Resume r WHERE r.idx = :resumeIdx")
    Optional<Resume> findByResumeIdx(Long resumeIdx);
}
