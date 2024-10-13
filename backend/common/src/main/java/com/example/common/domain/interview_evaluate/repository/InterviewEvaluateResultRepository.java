package com.example.common.domain.interview_evaluate.repository;

import com.example.common.domain.interview_evaluate.model.entity.InterviewEvaluateResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface InterviewEvaluateResultRepository extends JpaRepository<InterviewEvaluateResult, Long> { }
