package com.sabujaks.irs.domain.interview_schedule.repository;

import com.sabujaks.irs.domain.interview_schedule.model.entity.InterviewParticipate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InterviewParticipateRepository extends JpaRepository<InterviewParticipate, Long> {
    @Query("SELECT ip FROM InterviewParticipate ip WHERE ip.estimator.idx = :estimatorIdx AND ip.interviewSchedule.uuid = :interviewScheduleUUID")
    Optional<List<InterviewParticipate>> findFirstByEstimatorIdxAndInterviewScheduleUUID(Long estimatorIdx, String interviewScheduleUUID);

    @Query("SELECT ip FROM InterviewParticipate ip WHERE ip.estimator.idx = :estimatorIdx AND ip.interviewSchedule.uuid = :interviewScheduleUUID")
    Optional<List<InterviewParticipate>> findAllByEstimatorIdxAndInterviewScheduleUUID(Long estimatorIdx, String interviewScheduleUUID);

    List<InterviewParticipate> findByInterviewScheduleIdx(Long interviewScheduleIdx);
}
