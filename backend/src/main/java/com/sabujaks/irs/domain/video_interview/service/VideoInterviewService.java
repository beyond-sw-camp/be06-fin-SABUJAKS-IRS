package com.sabujaks.irs.domain.video_interview.service;

import com.sabujaks.irs.domain.video_interview.mdoel.entity.VideoInterviewRoom;
import com.sabujaks.irs.domain.video_interview.mdoel.request.VideoInterviewRoomCreateReq;
import com.sabujaks.irs.domain.video_interview.mdoel.response.VideoInterviewRoomCreateRes;
import com.sabujaks.irs.domain.video_interview.repository.VideoInterviewRepository;
import com.sabujaks.irs.global.common.exception.BaseException;
import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoInterviewService {
    private final OpenVidu openVidu;
    private final VideoInterviewRepository videoInterviewRepository;
    public VideoInterviewRoomCreateRes createRoom(VideoInterviewRoomCreateReq dto) throws OpenViduJavaClientException, OpenViduHttpException, BaseException {
        SessionProperties properties = SessionProperties.fromJson(dto.getParams()).build();
        Session session = openVidu.createSession(properties);
        System.out.println("################################" + dto.getAnnounceUUID());
        System.out.println("################################" + dto.getParams());
        System.out.println(session.getSessionId());
        VideoInterviewRoom videoInterviewRoom = VideoInterviewRoom.builder()
                .announceUUID(dto.getAnnounceUUID())
                .videoInterviewRoomUUID(session.getSessionId())
                .build();
        videoInterviewRepository.save(videoInterviewRoom);
        return VideoInterviewRoomCreateRes.builder()
                .idx(videoInterviewRoom.getIdx())
                .build();
    }
}
