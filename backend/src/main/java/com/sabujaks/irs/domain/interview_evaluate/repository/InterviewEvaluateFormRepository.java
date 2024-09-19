package com.sabujaks.irs.domain.interview_evaluate.repository;

import com.sabujaks.irs.domain.interview_evaluate.model.entity.InterviewEvaluate;
import com.sabujaks.irs.domain.interview_evaluate.model.entity.InterviewEvaluateForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface InterviewEvaluateFormRepository extends JpaRepository<InterviewEvaluateForm, Long> { }
