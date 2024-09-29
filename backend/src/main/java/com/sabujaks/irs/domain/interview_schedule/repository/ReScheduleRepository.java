package com.sabujaks.irs.domain.interview_schedule.repository;

import com.sabujaks.irs.domain.interview_schedule.model.entity.InterviewSchedule;
import com.sabujaks.irs.domain.interview_schedule.model.entity.ReSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReScheduleRepository extends JpaRepository<ReSchedule, Long> {

    @Query("SELECT r FROM ReSchedule r WHERE r.interviewSchedule.idx = :interviewScheduleIdx")
    ReSchedule findByInterviewScheduleIdx(Long interviewScheduleIdx);

    @Query("SELECT r FROM ReSchedule r WHERE r.idx = :reScheduleIdx")
    Optional<ReSchedule> findByReScheduleIdx(Long reScheduleIdx);
}
