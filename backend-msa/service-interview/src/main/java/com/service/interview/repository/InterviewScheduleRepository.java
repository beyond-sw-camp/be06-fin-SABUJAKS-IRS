package com.service.interview.repository;


import com.service.interview.entity.InterviewSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterviewScheduleRepository extends JpaRepository<InterviewSchedule, Long> {
    List<InterviewSchedule> findByInterviewDate(String interviewDate);

    @Query("SELECT is FROM InterviewSchedule is WHERE is.announcementIdx = :announcementIdx")
    Page<InterviewSchedule> findByAnnouncementIdx(Long announcementIdx, Pageable pageable);

    @Query("SELECT is FROM InterviewSchedule is WHERE is.careerBase = :careerBase AND is.announcementIdx = :announcementIdx")
    Page<InterviewSchedule> findByCareerBaseAndAnnouncementIdx(String careerBase, Long announcementIdx, Pageable pageable);

    @Query("SELECT is FROM InterviewSchedule is WHERE is.careerBase = :careerBase AND is.announcementIdx = :announcementIdx")
    Optional<List<InterviewSchedule>> findByCareerBaseAndAnnouncementIdx(String careerBase, Long announcementIdx);

    @Query("SELECT is FROM InterviewSchedule is WHERE is.idx = :interviewScheduleIdx")
    Optional<InterviewSchedule> findByInterviewScheduleIdx(Long interviewScheduleIdx);

    @Query("SELECT is FROM InterviewSchedule is WHERE is.announcementIdx = :announcementIdx")
    Optional<List<InterviewSchedule>> findByAnnouncementIdx(Long announcementIdx);

    @Query("SELECT DISTINCT is FROM InterviewSchedule is WHERE is.announcementUUID = :announcementUUID")
    List<InterviewSchedule> findByAnnouncementUUID(@Param("announcementUUID") String announcementUUID);

    @Query("SELECT DISTINCT is FROM InterviewSchedule is " +
            "JOIN FETCH is.interviewParticipateList ip " +
            "WHERE is.announcementUUID = :announcementUUID " +
            "AND ip.estimatorEmail = :estimatorEmail")
    List<InterviewSchedule> findByAnnouncementUUIDAndEstimatorEmail(@Param("announcementUUID") String announcementUUID, @Param("estimatorEmail") String estimatorEmail);

    @Query("SELECT DISTINCT is FROM InterviewSchedule is " +
            "JOIN FETCH is.interviewParticipateList ip " +
            "WHERE is.announcementUUID = :announcementUUID " +
            "AND ip.seekerIdx = :seekerIdx")
    List<InterviewSchedule> findByAnnouncementUUIDAndSeekerIdx(@Param("announcementUUID") String announcementUUID, @Param("seekerIdx") Long seekerIdx);

    @Query("SELECT is FROM InterviewSchedule is WHERE is.interviewScheduleUUID = :interviewScheduleUUID")
    Optional<InterviewSchedule> findByInterviewScheduleUUID(String interviewScheduleUUID);

    @Query("SELECT is FROM InterviewSchedule is WHERE is.interviewScheduleUUID = :interviewScheduleUUID")
    Optional<InterviewSchedule> findByUuid(String interviewScheduleUUID);

    @Query("SELECT is FROM InterviewSchedule is WHERE is.announcementIdx = :announcementIdx AND is.interviewNum = :interviewNum")
    Optional<List<InterviewSchedule>> findAllByAnnouncementIdxAndInterviewNum(Long announcementIdx, Integer interviewNum);

    @Query("SELECT is FROM InterviewSchedule is WHERE is.interviewDate = :interviewDate AND is.interviewTeam.idx = :teamIdx AND is.announcementIdx = :announcementIdx")
    Optional<List<InterviewSchedule>> findByInterviewDateAndTeamIdxAndAnnouncementIdx(String interviewDate, Long teamIdx, Long announcementIdx);

    @Query("SELECT COUNT(is) FROM InterviewSchedule is WHERE is.announcementIdx = :announcementIdx")
    Integer countInterviewScheduleByAnnouncementIdx(Long announcementIdx);
}