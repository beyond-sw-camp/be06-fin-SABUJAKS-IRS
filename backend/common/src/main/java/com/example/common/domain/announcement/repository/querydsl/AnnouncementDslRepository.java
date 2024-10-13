package com.example.common.domain.announcement.repository.querydsl;

import com.example.common.domain.announcement.model.entity.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementDslRepository extends JpaRepository<Announcement, Long> {
    @Query("SELECT a FROM Announcement a WHERE a.recruiter.idx = :recruiterIdx")
    Page<Announcement> findByRecruiterIdx(Long recruiterIdx, Pageable pageable);

    @Query("SELECT a FROM Announcement a WHERE a.recruiter.idx = :recruiterIdx AND a.careerBase = :careerBase")
    Page<Announcement> findByRecruiterIdxAndCareerBase(Long recruiterIdx, String careerBase, Pageable pageable);
}
