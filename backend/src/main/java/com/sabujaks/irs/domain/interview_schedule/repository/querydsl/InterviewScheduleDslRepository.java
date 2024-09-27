package com.sabujaks.irs.domain.interview_schedule.repository.querydsl;

import com.sabujaks.irs.domain.interview_schedule.model.entity.InterviewSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewScheduleDslRepository extends JpaRepository<InterviewSchedule, Long> {
    @Query("SELECT s FROM InterviewSchedule s WHERE s.announcement.idx = :announcementIdx")
    Page<InterviewSchedule> findByAnnouncementIdx(Long announcementIdx, Pageable pageable);

    @Query("SELECT s FROM InterviewSchedule s WHERE s.careerBase = :careerBase AND s.announcement.idx = :announcementIdx")
    Page<InterviewSchedule> findByCareerBaseAndAnnouncementIdx(String careerBase, Long announcementIdx, Pageable pageable);
}
