package com.service.resume.repository;

import com.service.resume.entity.SubmissionResume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SubmissionResumeRepository extends JpaRepository<SubmissionResume, Long> {
    @Query("SELECT sr FROM SubmissionResume sr WHERE sr.idx = :submissionResumeIdx")
    Optional<SubmissionResume> findBySubmissionResumeIdx(Long submissionResumeIdx);

    @Query("SELECT sr FROM SubmissionResume sr WHERE sr.announcementIdx = :announcementIdx AND sr.seekerIdx = :seekerIdx")
    Optional<SubmissionResume> findByAnnouncementIdxAndSeekerIdx(Long announcementIdx, Long seekerIdx);

    @Query("SELECT sr FROM SubmissionResume sr WHERE sr.seekerIdx = :seekerIdx ORDER BY sr.idx DESC")
    Optional<Page<SubmissionResume>> findAllBySeekerIdx(Long seekerIdx, Pageable pageable);

    @Query("SELECT COUNT(sr) FROM SubmissionResume sr WHERE sr.announcementIdx = :announcementIdx")
    Long countResumesByAnnouncementIdx(Long announcementIdx);

    @Query("SELECT sr FROM SubmissionResume sr WHERE sr.announcementIdx = :announcementIdx ORDER BY sr.idx DESC")
    Optional<Page<SubmissionResume>> findAllByAnnouncementIdx(Long announcementIdx, Pageable pageable);

}
