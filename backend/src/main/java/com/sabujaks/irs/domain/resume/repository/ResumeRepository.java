package com.sabujaks.irs.domain.resume.repository;

import com.sabujaks.irs.domain.resume.model.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
}
