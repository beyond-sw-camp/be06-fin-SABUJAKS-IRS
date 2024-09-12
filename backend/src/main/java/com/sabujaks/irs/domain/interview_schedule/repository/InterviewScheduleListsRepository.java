package com.sabujaks.irs.domain.interview_schedule.repository;

import com.sabujaks.irs.domain.interview_schedule.model.entity.InterviewScheduleLists;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewScheduleListsRepository extends JpaRepository<InterviewScheduleLists, Long> {
    List<InterviewScheduleLists> findByInterviewScheduleIdx(Long interviewScheduleIdx);
}
