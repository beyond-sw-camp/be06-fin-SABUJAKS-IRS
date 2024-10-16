package com.example.common.domain.announcement.repository;

import com.example.common.domain.announcement.model.entity.CustomForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CustomFormRepository extends JpaRepository<CustomForm, Long> {
    @Query("SELECT cf FROM CustomForm cf WHERE cf.announcement.idx = :announcementIdx")
    List<CustomForm> findAllByAnnouncementIdx(Long announcementIdx);
}