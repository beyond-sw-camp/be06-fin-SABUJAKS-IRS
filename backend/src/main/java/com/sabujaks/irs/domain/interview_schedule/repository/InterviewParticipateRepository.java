package com.sabujaks.irs.domain.interview_schedule.repository;

import com.sabujaks.irs.domain.interview_schedule.model.entity.InterviewParticipate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewParticipateRepository extends JpaRepository<InterviewParticipate, Long> {
    List<InterviewParticipate> findByInterviewScheduleIdx(Long interviewScheduleIdx);
}
