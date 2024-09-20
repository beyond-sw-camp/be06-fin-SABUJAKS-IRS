package com.sabujaks.irs.domain.interview_schedule.repository;

import com.sabujaks.irs.domain.auth.model.entity.Seeker;
import com.sabujaks.irs.domain.interview_schedule.model.entity.InterviewSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterviewScheduleRepository extends JpaRepository<InterviewSchedule, Long> {
    List<InterviewSchedule> findByInterviewDate(String interviewDate);

    List<InterviewSchedule> findByCareerBase(String careerBase);

    @Query("SELECT s FROM InterviewSchedule s WHERE s.idx = :interviewScheduleIdx")
    Optional<InterviewSchedule> findByInterviewScheduleIdx(Long interviewScheduleIdx);

    @Query("SELECT s FROM InterviewSchedule s WHERE s.announcement.idx = :announcementIdx")
    Optional<List<InterviewSchedule>> findByAnnouncementIdx(Long announcementIdx);

}
