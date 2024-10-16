package com.service.resume.repository;

import com.service.resume.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {
    @Query("SELECT t FROM Training t WHERE t.resume.idx = :resumeIdx")
    List<Training> findAllByResumeIdx(Long resumeIdx);
}
