package com.sabujaks.irs.domain.resume.repository;

import com.sabujaks.irs.domain.resume.model.entity.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, Long> {
    @Query("SELECT pi FROM PersonalInfo pi WHERE pi.resumeInfo.idx = :resumeInfoIdx")
    Optional<PersonalInfo> findByResumeInfoIdx(Long resumeInfoIdx);
}
