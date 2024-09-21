package com.sabujaks.irs.domain.resume.repository;

import com.sabujaks.irs.domain.resume.model.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    @Query("SELECT l FROM Language l WHERE l.resumeInfo.idx = :resumeInfoIdx")
    List<Language> findAllByResumeInfoIdx(Long resumeInfoIdx);
}
