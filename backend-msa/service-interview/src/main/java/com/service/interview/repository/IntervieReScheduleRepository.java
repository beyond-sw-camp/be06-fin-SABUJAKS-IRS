package com.service.interview.repository;

import com.service.interview.entity.InterviewReSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IntervieReScheduleRepository extends JpaRepository<InterviewReSchedule, Long> {

    @Query("SELECT r FROM InterviewReSchedule r WHERE r.interviewSchedule.idx = :interviewScheduleIdx")
    InterviewReSchedule findByInterviewScheduleIdx(Long interviewScheduleIdx);

    @Query("SELECT r FROM InterviewReSchedule r WHERE r.idx = :reScheduleIdx")
    Optional<InterviewReSchedule> findByReScheduleIdx(Long reScheduleIdx);

    @Query("SELECT COUNT(r) FROM InterviewReSchedule r WHERE r.interviewSchedule.idx = :interviewScheduleIdx")
    Integer countReScheduleByInterviewIdx(Long interviewScheduleIdx);
}
