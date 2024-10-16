package com.service.interview.repository;

import com.service.interview.entity.InterviewParticipate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InterviewParticipateRepository extends JpaRepository<InterviewParticipate, Long> {
    @Query("SELECT ip FROM InterviewParticipate ip WHERE ip.estimatorEmail = :estimatorEmail AND ip.interviewSchedule.interviewScheduleUUID = :interviewScheduleUUID")
    Optional<List<InterviewParticipate>> findFirstByEstimatorEmailAndInterviewScheduleUUID(String estimatorEmail, String interviewScheduleUUID);

    @Query("SELECT ip FROM InterviewParticipate ip WHERE ip.estimatorEmail = :estimatorEmail AND ip.interviewSchedule.interviewScheduleUUID = :interviewScheduleUUID")
    Optional<List<InterviewParticipate>> findAllByEstimatorIdxAndInterviewScheduleUUID(String estimatorEmail, String interviewScheduleUUID);

    // TODO: ????
    @Query("SELECT ip FROM InterviewParticipate ip WHERE " +
            "ip.seekerIdx = :seekerIdx AND " +
            "ip.estimatorEmail = :estimatorEmail AND " +
            "ip.interviewSchedule.interviewScheduleUUID = :interviewScheduleUUID")
    Optional<InterviewParticipate> findBySeekerIdxAndEstimatorEmailAndInterviewScheduleUUID(Long seekerIdx, String estimatorEmail, String interviewScheduleUUID);

    Optional<List<InterviewParticipate>> findByInterviewScheduleIdx(Long interviewScheduleIdx);

    @Query("SELECT ip FROM InterviewParticipate ip WHERE ip.interviewSchedule.idx = :interviewScheduleIdx AND ip.seekerIdx = :seekerIdx")
    Optional<InterviewParticipate> findByInterviewScheduleIdxAndSeekerIdx(Long interviewScheduleIdx, Long seekerIdx);

    @Query("SELECT ip FROM InterviewParticipate ip WHERE ip.interviewSchedule.idx = :interviewScheduleIdx AND ip.status = :status")
    Optional<List<InterviewParticipate>> findByInterviewScheduleIdxAndStatus(Long interviewScheduleIdx, Boolean status);
}
