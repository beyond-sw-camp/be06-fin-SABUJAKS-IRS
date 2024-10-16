package com.service.resume.repository;


import com.service.resume.entity.InternsActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InternActivitiesRepository extends JpaRepository<InternsActivity, Long> {
    @Query("SELECT ia FROM InternsActivity ia WHERE ia.resume.idx = :resumeIdx")
    List<InternsActivity> findAllByResumeIdx(Long resumeIdx);
}
