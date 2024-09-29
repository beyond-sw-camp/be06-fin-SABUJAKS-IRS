package com.sabujaks.irs.domain.announcement.repository;

import com.sabujaks.irs.domain.announcement.model.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    @Query("SELECT a FROM Announcement a WHERE a.idx = :announceIdx")
    Optional<Announcement> findByAnnounceIdx(Long announceIdx);

    @Query("SELECT a FROM Announcement a WHERE a.uuid = :announcementUUID")
    Optional<Announcement> findByAnnouncementUUID(String announcementUUID);

    @Query("SELECT a FROM Announcement a WHERE a.recruiter.idx = :recruiterIdx AND a.careerBase=:careerBase")
    Optional<List<Announcement>> findByRecruiterIdxAndCareerBase(Long recruiterIdx, String careerBase);

    @Query("SELECT a FROM Announcement a WHERE a.recruiter.idx = :recruiterIdx")
    Optional<List<Announcement>> findByRecruiterIdx(Long recruiterIdx);

    @Query("SELECT a FROM Announcement a WHERE a.title LIKE %:keyword% OR a.jobTitle LIKE %:keyword1%")
    Optional<List<Announcement>> findAllByTitleContainingOrjobTitleContaining(String keyword, String keyword1);
}