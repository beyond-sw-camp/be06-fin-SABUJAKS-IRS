package com.example.batch.processor;

import com.example.common.domain.announcement.model.entity.Announcement;
import com.example.common.domain.announcement.repository.AnnouncementRepository;
import com.example.common.domain.interview_schedule.model.entity.InterviewSchedule;
import com.example.common.domain.video_interview.model.entity.VideoInterview;
import io.openvidu.java.client.*;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VideoInterviewProcessor implements ItemProcessor<InterviewSchedule, VideoInterview> {

    private final OpenVidu openVidu;
    private final AnnouncementRepository announcementRepository;

    @Override
    public VideoInterview process(InterviewSchedule interviewSchedule) throws Exception {
        Optional<Announcement> announcement = announcementRepository.findByAnnounceIdx(interviewSchedule.getAnnouncement().getIdx());

        if(announcement.isPresent()) {
            Map<String, Object> params = new HashMap<>();
            params.put("customSessionId", interviewSchedule.getUuid());

            SessionProperties properties = SessionProperties.fromJson(params).build();
            Session session = openVidu.createSession(properties);

            return VideoInterview.builder()
                    .announceUUID(announcement.get().getUuid())
                    .videoInterviewRoomUUID(session.getSessionId())
                    .build();
        } else {
            return null;
        }
    }
}
