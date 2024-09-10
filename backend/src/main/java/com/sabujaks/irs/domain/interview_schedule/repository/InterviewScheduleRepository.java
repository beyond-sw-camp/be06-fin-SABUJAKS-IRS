package com.sabujaks.irs.domain.interview_schedule.repository;

import com.sabujaks.irs.domain.interview_schedule.model.entity.InterviewSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewScheduleRepository extends JpaRepository<InterviewSchedule, Long> {
}
