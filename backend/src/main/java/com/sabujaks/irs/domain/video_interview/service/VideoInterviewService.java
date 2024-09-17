package com.sabujaks.irs.domain.video_interview.service;

import com.sabujaks.irs.domain.auth.repository.EstimatorRepository;
import com.sabujaks.irs.domain.auth.repository.SeekerRepository;
import com.sabujaks.irs.domain.interview_schedule.model.entity.InterviewSchedule;
import com.sabujaks.irs.domain.interview_schedule.repository.InterviewScheduleRepository;
import com.sabujaks.irs.domain.video_interview.model.entity.VideoInterview;
import com.sabujaks.irs.domain.video_interview.model.request.VideoInterviewCreateReq;
import com.sabujaks.irs.domain.video_interview.model.request.VideoInterviewTokenGetReq;
import com.sabujaks.irs.domain.video_interview.model.response.VideoInterviewCreateRes;
import com.sabujaks.irs.domain.video_interview.model.response.VideoInterviewSearchRes;
import com.sabujaks.irs.domain.video_interview.model.response.VideoInterviewTokenGetRes;
import com.sabujaks.irs.domain.video_interview.repository.VideoInterviewRepository;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import com.sabujaks.irs.global.security.CustomUserDetails;
import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VideoInterviewService {
    private final OpenVidu openVidu;
    private final VideoInterviewRepository videoInterviewRepository;
    private final InterviewScheduleRepository interviewScheduleRepository;

    public VideoInterviewCreateRes create(VideoInterviewCreateReq dto) throws OpenViduJavaClientException, OpenViduHttpException {
        SessionProperties properties = SessionProperties.fromJson(dto.getParams()).build();
        Session session = openVidu.createSession(properties);
        VideoInterview videoInterviewRoom = VideoInterview.builder()
                .announceUUID(dto.getAnnounceUUID())
                .videoInterviewRoomUUID(session.getSessionId())
                .build();
        videoInterviewRepository.save(videoInterviewRoom);
        return VideoInterviewCreateRes.builder()
                .idx(videoInterviewRoom.getIdx())
                .build();
    }

    public List<VideoInterviewSearchRes> searchAll(String announceUUID, CustomUserDetails userDetails) throws BaseException {
        if(Objects.equals(userDetails.getRole(), "ROLE_ESTIMATOR")){
            List<InterviewSchedule> interviewScheduleList = interviewScheduleRepository.findByUuidAndEstimatorIdx(announceUUID, userDetails.getEstimator().getIdx());
            List<VideoInterviewSearchRes> videoInterviewSearchResList = new ArrayList<>();
            for(InterviewSchedule interviewSchedule : interviewScheduleList){
                VideoInterviewSearchRes videoInterviewSearchRes = VideoInterviewSearchRes.builder()
                        .announceUUID(announceUUID)
                        .videoInterviewUUID(interviewSchedule.getUuid())
                        .interviewDate(interviewSchedule.getInterviewDate())
                        .interviewStart(interviewSchedule.getInterviewStart())
                        .interviewEnd(interviewSchedule.getInterviewEnd())
                        .build();
                videoInterviewSearchResList.add(videoInterviewSearchRes);
            }
            return videoInterviewSearchResList;
        } else if (Objects.equals(userDetails.getRole(), "ROLE_SEEKER")) {
            List<InterviewSchedule> interviewScheduleList = interviewScheduleRepository.findByUuidAndSeekerIdx(announceUUID, userDetails.getSeeker().getIdx());
            List<VideoInterviewSearchRes> videoInterviewSearchResList = new ArrayList<>();
            for(InterviewSchedule interviewSchedule : interviewScheduleList){
                VideoInterviewSearchRes videoInterviewSearchRes = VideoInterviewSearchRes.builder()
                        .announceUUID(announceUUID)
                        .videoInterviewUUID(interviewSchedule.getUuid())
                        .interviewDate(interviewSchedule.getInterviewDate())
                        .interviewStart(interviewSchedule.getInterviewStart())
                        .interviewEnd(interviewSchedule.getInterviewEnd())
                        .build();
                videoInterviewSearchResList.add(videoInterviewSearchRes);
            }
            return videoInterviewSearchResList;
        } else {
            throw new BaseException(BaseResponseMessage.VIDEO_INTERVIEW_SEARCH_ALL_FAIL);
        }
    }
    public VideoInterviewTokenGetRes sessionToken(VideoInterviewTokenGetReq dto, CustomUserDetails userDetails) throws BaseException, OpenViduJavaClientException, OpenViduHttpException {
        Session session = openVidu.getActiveSession(dto.getVideoInterviewUUID());
        if (session == null) { throw new BaseException(BaseResponseMessage.VIDEO_INTERVIEW_JOIN_FAIL);}
        ConnectionProperties properties = ConnectionProperties.fromJson(dto.getParams()).build();
        Connection connection = session.createConnection(properties);
        return VideoInterviewTokenGetRes.builder()
                .sessionToken(connection.getToken())
                .userEmail(userDetails.getEmail())
                .userType(userDetails.getRole())
                .build();
    }
}

