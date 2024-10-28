package com.example.common.domain.announcement.repository;

import com.example.common.domain.announcement.model.entity.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    @Query("SELECT a FROM Announcement a WHERE a.idx = :announceIdx")
    Optional<Announcement> findByAnnounceIdx(Long announceIdx);

    @Query("SELECT a FROM Announcement a WHERE a.uuid = :announcementUUID")
    Optional<Announcement> findByAnnouncementUUID(String announcementUUID);

    @Query("SELECT a FROM Announcement a WHERE a.recruiter.idx = :recruiterIdx AND a.careerBase=:careerBase")
    Optional<List<Announcement>> findByRecruiterIdxAndCareerBase(Long recruiterIdx, String careerBase);

    @Query("SELECT a FROM Announcement a WHERE a.recruiter.idx = :recruiterIdx")
    Optional<List<Announcement>> findByRecruiterIdx(Long recruiterIdx);

    @Query("SELECT a FROM Announcement a WHERE a.recruiter.idx = :recruiterIdx ORDER BY a.idx DESC")
    Page<Announcement> findByRecruiterIdx(Long recruiterIdx, Pageable pageable);

    @Query("SELECT a FROM Announcement a WHERE a.recruiter.idx = :recruiterIdx")
    Page<Announcement> findAllByRecruiterIdx(Long recruiterIdx, Pageable pageable);

    @Query("SELECT a FROM Announcement a WHERE a.recruiter.idx = :recruiterIdx AND a.careerBase = :careerBase")
    Page<Announcement> findAllByRecruiterIdxAndCareerBase(Long recruiterIdx, String careerBase, Pageable pageable);

    @Query("SELECT a FROM Announcement a WHERE a.deadlineDocument = :deadlineDocument")
    List<Announcement> findAllByDeadlineDocument(String deadlineDocument);
}