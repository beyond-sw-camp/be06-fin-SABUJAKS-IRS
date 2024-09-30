package com.sabujaks.irs.domain.total_process.repository;

import com.sabujaks.irs.domain.interview_evaluate.model.entity.InterviewEvaluate;
import com.sabujaks.irs.domain.total_process.model.entity.TotalProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface TotalProcessRepository extends JpaRepository<TotalProcess, Long> {
    @Query("SELECT tp FROM TotalProcess tp " +
            "WHERE tp.announcement.idx = :announcementIdx " +
            "AND tp.seeker.idx = :seekerIdx")
    Optional<TotalProcess> findByAnnouncementIdxAndSeekerIdx(Long announcementIdx, Long seekerIdx);
}