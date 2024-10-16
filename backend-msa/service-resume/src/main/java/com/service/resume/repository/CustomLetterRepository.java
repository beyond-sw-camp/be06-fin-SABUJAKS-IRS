package com.service.resume.repository;

import com.service.resume.entity.CustomLetter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomLetterRepository extends JpaRepository<CustomLetter, Long> {
    @Query("SELECT cl FROM CustomLetter cl WHERE cl.resume.idx = :resumeIdx")
    List<CustomLetter> findAllByResumeIdx(Long resumeIdx);
}
