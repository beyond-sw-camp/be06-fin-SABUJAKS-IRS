package com.service.announcement.repository;

import com.service.announcement.entity.CustomLetterForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomLetterFormRepository extends JpaRepository<CustomLetterForm, Long> {
    @Query("SELECT clf FROM CustomLetterForm clf WHERE clf.announcement.idx = :announcementIdx")
    List<CustomLetterForm> findAllByAnnouncementIdx(Long announcementIdx);
}