package com.example.common.domain.total_process.repository;

import com.example.common.domain.total_process.model.entity.TotalProcess;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("SELECT tp FROM TotalProcess tp " +
            "WHERE tp.announcement.idx = :announcementIdx " +
            "AND tp.resumeResult = :resumeResult")
    List<TotalProcess> findByAnnouncementIdxAndResumeResult(Long announcementIdx, Boolean resumeResult);

    @Query("SELECT tp FROM TotalProcess tp WHERE tp.announcement.idx = :announcementIdx ORDER BY tp.idx DESC")
    Page<TotalProcess> findAllByAnnouncementIdx(Long announcementIdx, Pageable pageable);
}