package com.service.resume.repository;

import com.service.resume.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    @Query("SELECT l FROM Language l WHERE l.resume.idx = :resumeIdx")
    List<Language> findAllByResumeIdx(Long resumeIdx);
}
