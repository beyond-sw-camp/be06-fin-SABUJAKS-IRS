package com.service.resume.repository;

import com.service.resume.entity.PreferentialEmp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PreferentialEmpRepository extends JpaRepository<PreferentialEmp, Long> {
    @Query("SELECT pm FROM PreferentialEmp pm WHERE pm.resume.idx = :resumeIdx")
    Optional<PreferentialEmp> findByResumeIdx(Long resumeIdx);
}
