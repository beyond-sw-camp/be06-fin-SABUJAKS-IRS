package com.example.common.domain.resume.repository;

import com.example.common.domain.resume.model.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface LanguageRepository extends JpaRepository<Language, Long> {
    @Query("SELECT l FROM Language l WHERE l.resumeInfo.idx = :resumeInfoIdx")
    List<Language> findAllByResumeInfoIdx(Long resumeInfoIdx);
}
