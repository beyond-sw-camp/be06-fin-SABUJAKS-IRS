package com.example.common.domain.interview_schedule.repository;

import com.example.common.domain.interview_schedule.model.entity.InterviewParticipate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface InterviewParticipateRepository extends JpaRepository<InterviewParticipate, Long> {
    @Query("SELECT ip FROM InterviewParticipate ip WHERE ip.estimator.idx = :estimatorIdx AND ip.interviewSchedule.uuid = :interviewScheduleUUID")
    Optional<List<InterviewParticipate>> findFirstByEstimatorIdxAndInterviewScheduleUUID(Long estimatorIdx, String interviewScheduleUUID);

    @Query("SELECT ip FROM InterviewParticipate ip WHERE ip.estimator.idx = :estimatorIdx AND ip.interviewSchedule.uuid = :interviewScheduleUUID")
    Optional<List<InterviewParticipate>> findAllByEstimatorIdxAndInterviewScheduleUUID(Long estimatorIdx, String interviewScheduleUUID);

    @Query("SELECT ip FROM InterviewParticipate ip WHERE " +
            "ip.seeker.email = :seekerEmail AND " +
            "ip.estimator.idx = :estimatorIdx AND " +
            "ip.interviewSchedule.uuid = :interviewScheduleUUID")
    Optional<InterviewParticipate> findBySeekerEmailAndEstimatorIdxAndInterviewScheduleUUID(String seekerEmail, Long estimatorIdx, String interviewScheduleUUID);

    Optional<List<InterviewParticipate>> findByInterviewScheduleIdx(Long interviewScheduleIdx);

    @Query("SELECT ip FROM InterviewParticipate ip WHERE ip.interviewSchedule.idx = :interviewScheduleIdx AND ip.seeker.idx = :seekerIdx")
    Optional<InterviewParticipate> findByInterviewScheduleIdxAndSeekerIdx(Long interviewScheduleIdx, Long seekerIdx);

    @Query("SELECT ip FROM InterviewParticipate ip WHERE ip.interviewSchedule.idx = :interviewScheduleIdx AND ip.status = :status")
    Optional<List<InterviewParticipate>> findByInterviewScheduleIdxAndStatus(Long interviewScheduleIdx, Boolean status);
}
