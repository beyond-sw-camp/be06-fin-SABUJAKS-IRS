package com.sabujaks.irs.domain.interview_evaluate.repository;

import com.sabujaks.irs.domain.interview_evaluate.model.entity.InterviewEvaluateResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
public interface InterviewEvaluateResultRepository extends JpaRepository<InterviewEvaluateResult, Long> { }
