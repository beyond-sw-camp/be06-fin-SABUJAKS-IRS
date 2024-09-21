package com.sabujaks.irs.domain.interview_schedule.repository;

import com.sabujaks.irs.domain.interview_schedule.model.entity.InterviewParticipate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface InterviewParticipateRepository extends JpaRepository<InterviewParticipate, Long> {
    @Query("SELECT ip FROM InterviewParticipate ip WHERE estimator.idx = :estimatorIdx AND interviewSchedule.uuid = :interviewScheduleUUID")
    Optional<InterviewParticipate> findByEstimatorIdxAndInterviewScheduleUUID(Long estimatorIdx, String interviewScheduleUUID);
}
