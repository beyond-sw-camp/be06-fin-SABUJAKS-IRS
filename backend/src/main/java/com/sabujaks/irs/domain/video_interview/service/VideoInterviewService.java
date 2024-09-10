package com.sabujaks.irs.domain.video_interview.service;

import com.sabujaks.irs.domain.video_interview.mdoel.entity.VideoInterview;
import com.sabujaks.irs.domain.video_interview.mdoel.request.VideoInterviewCreateReq;
import com.sabujaks.irs.domain.video_interview.mdoel.response.VideoInterviewCreateRes;
import com.sabujaks.irs.domain.video_interview.mdoel.response.VideoInterviewSearchRes;
import com.sabujaks.irs.domain.video_interview.repository.VideoInterviewRepository;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VideoInterviewService {
    private final OpenVidu openVidu;
    private final VideoInterviewRepository videoInterviewRepository;
    public VideoInterviewCreateRes create(VideoInterviewCreateReq dto) throws OpenViduJavaClientException, OpenViduHttpException {
        SessionProperties properties = SessionProperties.fromJson(dto.getParams()).build();
        Session session = openVidu.createSession(properties);
        VideoInterview videoInterviewRoom = VideoInterview.builder()
                .announceUUID(dto.getAnnounceUUID())
                .interviewScheduleUUID(session.getSessionId())
                .build();
        videoInterviewRepository.save(videoInterviewRoom);
        return VideoInterviewCreateRes.builder()
                .idx(videoInterviewRoom.getIdx())
                .build();
    }

    public List<VideoInterviewSearchRes> searchAll(String announceUUID) throws BaseException{
        List<VideoInterview> result =  videoInterviewRepository.findAllByAnnounceUUID(announceUUID)
        .orElseThrow( () -> new BaseException(BaseResponseMessage.VIDEO_INTERVIEW_SEARCH_FAIL_NOT_FOUND));
        List<VideoInterviewSearchRes> videoInterviewSearchResList = new ArrayList<>();
        for (VideoInterview videoInterview : result) {
            VideoInterviewSearchRes videoInterviewSearchRes = VideoInterviewSearchRes.builder()
                    .idx(videoInterview.getIdx())
                    .announceUUID(videoInterview.getAnnounceUUID())
                    .interviewScheduleUUID(videoInterview.getInterviewScheduleUUID())
                    .build();
            videoInterviewSearchResList.add(videoInterviewSearchRes);
        }
        return videoInterviewSearchResList;
    }
}
