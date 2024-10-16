package com.service.resume.repository;


import com.service.resume.entity.PersonalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonalHistoryRepository extends JpaRepository<PersonalHistory, Long> {
    @Query("SELECT ph FROM PersonalHistory ph WHERE ph.resume.idx = :resumeIdx")
    List<PersonalHistory> findAllByResumeIdx(Long resumeIdx);
}
