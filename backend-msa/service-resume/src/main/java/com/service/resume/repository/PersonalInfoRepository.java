package com.service.resume.repository;

import com.service.resume.entity.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, Long> {
    @Query("SELECT pi FROM PersonalInfo pi WHERE pi.resume.idx = :resumeIdx")
    Optional<PersonalInfo> findByResumeIdx(Long resumeIdx);
}
