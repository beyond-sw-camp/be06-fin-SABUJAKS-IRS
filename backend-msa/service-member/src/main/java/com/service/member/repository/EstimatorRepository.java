package com.service.member.repository;

import com.service.member.entity.Estimator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EstimatorRepository extends JpaRepository<Estimator, Long> {

    @Query("SELECT e FROM Estimator e WHERE e.email = :estimatorEmail")
    Optional<Estimator> findByEstimatorEmail(String estimatorEmail);

    @Query("SELECT e FROM Estimator e WHERE e.idx = :estimatorIdx")
    Optional<Estimator> findByEstimatorIdx(Long estimatorIdx);
}
