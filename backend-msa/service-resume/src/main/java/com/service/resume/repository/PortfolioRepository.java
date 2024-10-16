package com.service.resume.repository;

import com.service.resume.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, Integer> {
    @Query("SELECT p FROM Portfolio p WHERE p.resume.idx = :resumeIdx")
    List<Portfolio> findAllByResumeIdx(Long resumeIdx);
}
