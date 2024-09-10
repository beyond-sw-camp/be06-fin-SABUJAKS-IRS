package com.sabujaks.irs.domain.video_interview.repository;

import com.sabujaks.irs.domain.video_interview.mdoel.entity.VideoInterviewRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoInterviewRepository extends JpaRepository<VideoInterviewRoom, Long> {
}
