package com.sabujaks.irs.domain.video_interview.repository;

import com.sabujaks.irs.domain.auth.model.entity.CompanyVerify;
import com.sabujaks.irs.domain.video_interview.mdoel.entity.VideoInterview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VideoInterviewRepository extends JpaRepository<VideoInterview, Long> {
    @Query("SELECT vi FROM VideoInterview vi WHERE vi.announceUUID = :announceUUID")
    Optional<List<VideoInterview>> findAllByAnnounceUUID(String announceUUID);

}
