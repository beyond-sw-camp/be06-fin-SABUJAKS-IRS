package com.sabujaks.irs.domain.resume.repository;

import com.sabujaks.irs.domain.resume.model.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, Integer> {
    @Query("SELECT p FROM Portfolio p WHERE p.resumeInfo.idx = :resumeInfoIdx")
    List<Portfolio> findAllByResumeInfoIdx(Long resumeInfoIdx);
}
