package com.sabujaks.irs.domain.interview_schedule.repository;

import com.sabujaks.irs.domain.interview_schedule.model.entity.InterviewSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewScheduleRepository extends JpaRepository<InterviewSchedule, Long> {
    List<InterviewSchedule> findByInterviewDate(String interviewDate);

    @Query("SELECT DISTINCT is FROM InterviewSchedule is " +
            "JOIN FETCH is.announcement a " +
            "WHERE a.uuid = :uuid")
    List<InterviewSchedule> findByAnnounceUUID(@Param("uuid") String announceUUID);

    @Query("SELECT DISTINCT is FROM InterviewSchedule is " +
            "JOIN FETCH is.announcement a " +
            "JOIN FETCH is.interviewParticipateList ip " +
            "WHERE a.uuid = :uuid AND ip.estimator.idx = :estimatorIdx")
    List<InterviewSchedule> findByUuidAndEstimatorIdx(@Param("uuid") String announceUUID, @Param("estimatorIdx") Long estimatorIdx);


    @Query("SELECT DISTINCT is FROM InterviewSchedule is " +
            "JOIN FETCH is.announcement a " +
            "JOIN FETCH is.interviewParticipateList ip " +
            "WHERE a.uuid = :uuid AND ip.seeker.idx = :seekerIdx")
    List<InterviewSchedule> findByUuidAndSeekerIdx(@Param("uuid") String announceUUID, @Param("seekerIdx") Long seekerIdx);
}
