package com.sabujaks.irs.domain.resume.repository;

import com.sabujaks.irs.domain.resume.model.entity.Resume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    @Query("SELECT r FROM Resume r WHERE r.idx = :resumeIdx")
    Optional<Resume> findByResumeIdx(Long resumeIdx);

    @Query("SELECT r FROM Resume r WHERE r.announcement.idx = :announcementIdx AND r.seeker.idx = :seekerIdx")
    Optional<Resume> findByAnnouncementIdxAndSeekerIdx(Long announcementIdx, Long seekerIdx);

    @Query("SELECT r FROM Resume r WHERE r.seeker.idx = :seekerIdx")
    List<Resume> findAllBySeekerIdx(Long seekerIdx);

    @Query("SELECT COUNT(r) FROM Resume r WHERE r.announcement.idx = :announcementIdx")
    Long countResumesByAnnouncementIdx(Long announcementIdx);

    @Query("SELECT r FROM Resume r WHERE r.announcement.idx = :announcementIdx ORDER BY r.idx DESC")
    Page<Resume> findAllByAnnouncementIdx(Long announcementIdx, Pageable pageable);
}
