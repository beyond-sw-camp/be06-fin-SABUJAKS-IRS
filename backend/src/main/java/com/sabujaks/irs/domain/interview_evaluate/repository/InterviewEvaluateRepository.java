package com.sabujaks.irs.domain.interview_evaluate.repository;

import com.sabujaks.irs.domain.interview_evaluate.model.entity.InterviewEvaluate;
import com.sabujaks.irs.domain.interview_evaluate.model.entity.InterviewEvaluateForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories
public interface InterviewEvaluateRepository extends JpaRepository<InterviewEvaluate, Long> {
    @Query("SELECT ie FROM InterviewEvaluate ie WHERE ie.interviewParticipate.idx = :interviewParticipateIdx")
    Optional<InterviewEvaluate> findByInterviewParticipateIdx(Long interviewParticipateIdx);

}

