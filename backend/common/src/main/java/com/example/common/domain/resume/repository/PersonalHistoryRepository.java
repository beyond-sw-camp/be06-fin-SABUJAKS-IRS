package com.example.common.domain.resume.repository;


import com.example.common.domain.resume.model.entity.PersonalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PersonalHistoryRepository extends JpaRepository<PersonalHistory, Long> {
    @Query("SELECT ph FROM PersonalHistory ph WHERE ph.resumeInfo.idx = :resumeInfoIdx")
    List<PersonalHistory> findAllByResumeInfoIdx(Long resumeInfoIdx);
}
