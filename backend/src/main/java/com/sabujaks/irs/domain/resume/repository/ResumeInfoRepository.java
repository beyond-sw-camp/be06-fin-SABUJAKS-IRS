package com.sabujaks.irs.domain.resume.repository;

import com.sabujaks.irs.domain.auth.model.entity.Seeker;
import com.sabujaks.irs.domain.resume.model.entity.ResumeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ResumeInfoRepository extends JpaRepository<ResumeInfo, Long> {
    @Query("SELECT r FROM ResumeInfo r WHERE r.seeker.idx = :seekerIdx AND r.integrated = :integration")
    Optional<ResumeInfo> findBySeekerIdxAndIntegrated(Long seekerIdx, Boolean integration);
}
