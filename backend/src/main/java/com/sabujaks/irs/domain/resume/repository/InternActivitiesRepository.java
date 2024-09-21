package com.sabujaks.irs.domain.resume.repository;

import com.sabujaks.irs.domain.resume.model.entity.InternsActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InternActivitiesRepository extends JpaRepository<InternsActivity, Long> {
    @Query("SELECT ia FROM InternsActivity ia WHERE ia.resumeInfo.idx = :resumeInfoIdx")
    List<InternsActivity> findAllByResumeInfoIdx(Long resumeInfoIdx);
}
