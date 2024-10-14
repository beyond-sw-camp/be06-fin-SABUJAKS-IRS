package com.sabujaks.irs.domain.announcement.repository;

import com.sabujaks.irs.domain.announcement.model.entity.Announcement;
import com.sabujaks.irs.domain.announcement.model.response.AnnouncementReadAllRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("SELECT a FROM Announcement a WHERE a.recruiter.idx = :recruiterIdx ORDER BY a.idx DESC")
    Page<Announcement> findByRecruiterIdx(Long recruiterIdx, Pageable pageable);

    @Query("SELECT a FROM Announcement a WHERE a.recruiter.idx = :recruiterIdx")
    Page<Announcement> findAllByRecruiterIdx(Long recruiterIdx, Pageable pageable);

    @Query("SELECT a FROM Announcement a WHERE a.recruiter.idx = :recruiterIdx AND a.careerBase = :careerBase")
    Page<Announcement> findAllByRecruiterIdxAndCareerBase(Long recruiterIdx, String careerBase, Pageable pageable);
}