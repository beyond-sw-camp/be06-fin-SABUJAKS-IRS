package com.service.resume.repository;

import com.service.resume.entity.Award;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AwardRepository extends JpaRepository<Award, Long> {
    @Query("SELECT a FROM Award a WHERE a.resume.idx = :resumeIdx")
    List<Award> findAllByResumeIdx(Long resumeIdx);
}
