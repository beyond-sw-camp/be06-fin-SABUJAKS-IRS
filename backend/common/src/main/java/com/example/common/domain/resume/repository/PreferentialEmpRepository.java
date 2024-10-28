package com.example.common.domain.resume.repository;

import com.example.common.domain.resume.model.entity.PreferentialEmp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface PreferentialEmpRepository extends JpaRepository<PreferentialEmp, Long> {
    @Query("SELECT pm FROM PreferentialEmp pm WHERE pm.resumeInfo.idx = :resumeInfoIdx")
    Optional<PreferentialEmp> findByResumeInfoIdx(Long resumeInfoIdx);
}
