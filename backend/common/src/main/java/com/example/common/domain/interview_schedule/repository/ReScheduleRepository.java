package com.example.common.domain.interview_schedule.repository;

import com.example.common.domain.interview_schedule.model.entity.ReSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReScheduleRepository extends JpaRepository<ReSchedule, Long> {

    @Query("SELECT r FROM ReSchedule r WHERE r.interviewSchedule.idx = :interviewScheduleIdx")
    List<ReSchedule> findAllByInterviewScheduleIdx(Long interviewScheduleIdx);

    @Query("SELECT r FROM ReSchedule r WHERE r.idx = :reScheduleIdx")
    Optional<ReSchedule> findByReScheduleIdx(Long reScheduleIdx);

    @Query("SELECT COUNT(r) FROM ReSchedule r WHERE r.interviewSchedule.idx = :interviewScheduleIdx")
    Integer countReScheduleByInterviewIdx(Long interviewScheduleIdx);
}
