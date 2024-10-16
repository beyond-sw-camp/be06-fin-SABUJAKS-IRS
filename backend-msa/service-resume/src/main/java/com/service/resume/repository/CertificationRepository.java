package com.service.resume.repository;

import com.service.resume.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CertificationRepository extends JpaRepository<Certification, Integer> {
    @Query("SELECT c FROM Certification c WHERE c.resume.idx = :resumeIdx")
    List<Certification> findAllByResumeIdx(Long resumeIdx);
}
