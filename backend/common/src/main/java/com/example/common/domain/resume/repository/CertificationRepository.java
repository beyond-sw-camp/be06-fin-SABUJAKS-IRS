package com.example.common.domain.resume.repository;

import com.example.common.domain.resume.model.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CertificationRepository extends JpaRepository<Certification, Integer> {
    @Query("SELECT c FROM Certification c WHERE c.resumeInfo.idx = :resumeInfoIdx")
    List<Certification> findAllByResumeInfoIdx(Long resumeInfoIdx);
}
