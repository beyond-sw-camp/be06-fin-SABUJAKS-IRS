package com.sabujaks.irs.domain.auth.repository;

import com.sabujaks.irs.domain.interview_schedule.model.entity.Estimator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EstimatorRepository extends JpaRepository<Estimator, Long> {
    @Query("SELECT e FROM Estimator e WHERE e.email = :estimatorEmail")
    Optional<Estimator> findByEstimatorEmail(String estimatorEmail);
}
