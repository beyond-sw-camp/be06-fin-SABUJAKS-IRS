package com.service.interview.repository;

import com.service.interview.entity.InterviewEvaluate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface InterviewEvaluateRepository extends JpaRepository<InterviewEvaluate, Long> {
    @Query("SELECT ie FROM InterviewEvaluate ie WHERE ie.interviewParticipate.idx = :interviewParticipateIdx")
    Optional<InterviewEvaluate> findByInterviewParticipateIdx(Long interviewParticipateIdx);

    @Query("SELECT ie FROM InterviewEvaluate ie " +
            "WHERE ie.interviewParticipate.idx = :interviewParticipateIdx " +
            "AND ie.interviewParticipate.seekerIdx = :seekerIdx")
    Optional<List<InterviewEvaluate>> findAllByInterviewParticipateIdxAndSeekerIdx(Long interviewParticipateIdx, Long seekerIdx);
}

