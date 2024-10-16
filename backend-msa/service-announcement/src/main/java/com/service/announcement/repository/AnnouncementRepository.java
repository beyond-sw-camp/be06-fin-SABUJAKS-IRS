package com.service.announcement.repository;

import com.service.announcement.entity.Announcement;
import com.service.common.dto.response.announcement.SearchAnnouncementReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    @Query("SELECT a FROM Announcement a WHERE a.idx = :announcementIdx")
    Optional<Announcement> findByAnnouncementIdx(Long announcementIdx);

    @Query("SELECT a FROM Announcement a WHERE a.uuid = :announcementUUID")
    Optional<Announcement> findByAnnouncementUUID(String announcementUUID);

    @Query("SELECT a FROM Announcement a WHERE a.recruiterIdx = :recruiterIdx AND a.careerBase=:careerBase")
    Optional<List<Announcement>> findByRecruiterIdxAndCareerBase(Long recruiterIdx, String careerBase);

    @Query("SELECT a FROM Announcement a WHERE a.recruiterIdx = :recruiterIdx")
    Optional<List<Announcement>> findByRecruiterIdx(Long recruiterIdx);

    @Query("SELECT a FROM Announcement a WHERE a.recruiterIdx = :recruiterIdx ORDER BY a.idx DESC")
    Page<Announcement> findByRecruiterIdx(Long recruiterIdx, Pageable pageable);

//    Page<Announcement> search(Pageable pageable, SearchAnnouncementReq searchAnnouncementReq);
    // TODO: 쓰면 고치고 안쓰면 지우기
//    @Query("SELECT a FROM Announcement a " +
//            "JOIN Company c ON a.recruiter = c.recruiter " +
//            "WHERE (a.title LIKE %:keyword% OR a.jobTitle LIKE %:keyword% OR c.name LIKE %:keyword%)")
//    Optional<List<Announcement>> findAllByKeywordInTitleOrJobTitleOrCompanyName(String keyword);

    List<Announcement> findAllByCareerBase(String careerBase);

    @Query("SELECT a FROM Announcement a WHERE a.jobCategory LIKE %:jobCategory%")
    List<Announcement> findAllByJobCategoryContaining(String jobCategory);

    @Query("SELECT a FROM Announcement a WHERE a.region LIKE %:region%")
    List<Announcement> findAllByRegionContaining(String region);

    @Query("SELECT a FROM Announcement a WHERE a.careerBase = :careerBase AND a.jobCategory LIKE %:jobCategory%")
    List<Announcement> findAllByCareerBaseAndJobCategoryContaining(String careerBase, String jobCategory);

    @Query("SELECT a FROM Announcement a WHERE a.careerBase = :careerBase AND a.region LIKE %:region%")
    List<Announcement> findAllByCareerBaseAndRegionContaining(String careerBase, String region);

    @Query("SELECT a FROM Announcement a WHERE a.jobCategory LIKE %:jobCategory% AND a.region LIKE %:region%")
    List<Announcement> findAllByJobCategoryContainingAndRegionContaining(String jobCategory, String region);

    @Query("SELECT a FROM Announcement a WHERE a.careerBase = :careerBase AND a.jobCategory LIKE %:jobCategory% AND a.region LIKE %:region%")
    List<Announcement> findAllByCareerBaseAndJobCategoryContainingAndRegionContaining(String careerBase, String jobCategory, String region);

    @Query("SELECT a FROM Announcement a WHERE a.recruiterIdx = :recruiterIdx")
    Page<Announcement> findAllByRecruiterIdx(Long recruiterIdx, Pageable pageable);

    @Query("SELECT a FROM Announcement a WHERE a.recruiterIdx = :recruiterIdx AND a.careerBase = :careerBase")
    Page<Announcement> findAllByRecruiterIdxAndCareerBase(Long recruiterIdx, String careerBase, Pageable pageable);
}