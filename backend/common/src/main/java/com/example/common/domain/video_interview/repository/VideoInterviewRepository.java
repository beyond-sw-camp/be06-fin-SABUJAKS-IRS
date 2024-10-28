package com.example.common.domain.video_interview.repository;


import com.example.common.domain.video_interview.model.entity.VideoInterview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoInterviewRepository extends JpaRepository<VideoInterview, Long> {}
