package com.service.announcement.repository;

import com.service.announcement.entity.CustomResumeForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomResumeFormRepository extends JpaRepository<CustomResumeForm, Long> {
    @Query("SELECT cf FROM CustomResumeForm cf WHERE cf.announcement.idx = :announcementIdx")
    Optional<CustomResumeForm> findByAnnouncementIdx(Long announcementIdx);
    @Query("SELECT cf FROM CustomResumeForm cf WHERE cf.announcement.idx = :announcementIdx")
    List<CustomResumeForm> findAllByAnnouncementIdx(Long announcementIdx);
}