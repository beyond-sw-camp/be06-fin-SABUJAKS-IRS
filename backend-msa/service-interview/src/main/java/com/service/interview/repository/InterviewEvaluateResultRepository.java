package com.service.interview.repository;


import com.service.interview.entity.InterviewEvaluateResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface InterviewEvaluateResultRepository extends JpaRepository<InterviewEvaluateResult, Long> { }
