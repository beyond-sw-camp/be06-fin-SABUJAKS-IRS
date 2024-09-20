package com.sabujaks.irs.domain.video_interview.repository;

import com.sabujaks.irs.domain.video_interview.model.entity.VideoInterview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VideoInterviewRepository extends JpaRepository<VideoInterview, Long> {}
