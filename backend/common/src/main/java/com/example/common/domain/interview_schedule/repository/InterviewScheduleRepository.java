package com.example.common.domain.interview_schedule.repository;

import com.example.common.domain.interview_schedule.model.entity.InterviewSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface InterviewScheduleRepository extends JpaRepository<InterviewSchedule, Long> {
    List<InterviewSchedule> findByInterviewDate(String interviewDate);
    List<InterviewSchedule> findByCareerBase(String careerBase);

    @Query("SELECT s FROM InterviewSchedule s WHERE s.careerBase = :careerBase AND s.announcement.idx = :announcementIdx")
    Optional<List<InterviewSchedule>> findByCareerBaseAndAnnouncementIdx(String careerBase, Long announcementIdx);

    @Query("SELECT s FROM InterviewSchedule s WHERE s.idx = :interviewScheduleIdx")
    Optional<InterviewSchedule> findByInterviewScheduleIdx(Long interviewScheduleIdx);

    @Query("SELECT s FROM InterviewSchedule s WHERE s.announcement.idx = :announcementIdx")
    Optional<List<InterviewSchedule>> findByAnnouncementIdx(Long announcementIdx);

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

    @Query("SELECT is FROM InterviewSchedule is WHERE is.uuid = :interviewScheduleUUID")
    Optional<InterviewSchedule> findByInterviewScheduleUUID(String interviewScheduleUUID);

    @Query("SELECT is FROM InterviewSchedule is WHERE is.uuid = :interviewScheduleUUID")
    Optional<InterviewSchedule> findByUuid(String interviewScheduleUUID);

    @Query("SELECT is FROM InterviewSchedule is WHERE is.announcement.idx = :announcementIdx AND is.interviewNum = :interviewNum")
    Optional<List<InterviewSchedule>> findAllByAnnouncementIdxAndInterviewNum(Long announcementIdx, Integer interviewNum);

    @Query("SELECT is FROM InterviewSchedule is WHERE is.interviewDate = :interviewDate AND is.team.idx = :teamIdx AND is.announcement.idx = :announcementIdx")
    Optional<List<InterviewSchedule>> findByInterviewDateAndTeamIdxAndAnnouncementIdx(String interviewDate, Long teamIdx, Long announcementIdx);

    @Query("SELECT COUNT(is) FROM InterviewSchedule is WHERE is.announcement.idx = :announcementIdx")
    Integer countInterviewScheduleByAnnouncementIdx(Long announcementIdx);

    @Query("SELECT is FROM InterviewSchedule is WHERE is.announcement.idx = :announcementIdx")
    Optional<List<InterviewSchedule>> findAllByAnnouncementIdx(Long announcementIdx);

}