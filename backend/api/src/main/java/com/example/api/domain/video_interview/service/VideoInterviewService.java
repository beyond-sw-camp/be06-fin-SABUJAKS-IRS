package com.example.api.domain.video_interview.service;

import com.example.common.domain.interview_schedule.model.entity.InterviewSchedule;
import com.example.common.domain.interview_schedule.repository.InterviewScheduleRepository;
import com.example.common.domain.video_interview.model.entity.VideoInterview;
import com.example.common.domain.video_interview.repository.VideoInterviewRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.api.domain.video_interview.model.request.VideoInterviewCreateReq;
import com.example.api.domain.video_interview.model.request.VideoInterviewTokenGetReq;
import com.example.api.domain.video_interview.model.response.VideoInterviewCreateRes;
import com.example.api.domain.video_interview.model.response.VideoInterviewReadRes;
import com.example.api.domain.video_interview.model.response.VideoInterviewTokenGetRes;
import com.example.api.global.common.exception.BaseException;
import com.example.api.global.common.responses.BaseResponseMessage;
import com.example.api.global.security.CustomUserDetails;
import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class VideoInterviewService {
    private final OpenVidu openVidu;
    private final VideoInterviewRepository videoInterviewRepository;
    private final InterviewScheduleRepository interviewScheduleRepository;
    private final TaskScheduler taskScheduler;

    public VideoInterviewService(OpenVidu openVidu, VideoInterviewRepository videoInterviewRepository, InterviewScheduleRepository interviewScheduleRepository, TaskScheduler taskScheduler) {
        this.openVidu = openVidu;
        this.videoInterviewRepository = videoInterviewRepository;
        this.interviewScheduleRepository = interviewScheduleRepository;
        this.taskScheduler = taskScheduler;
    }

    public VideoInterviewCreateRes create(VideoInterviewCreateReq dto) throws OpenViduJavaClientException, OpenViduHttpException, BaseException {
        SessionProperties properties = SessionProperties.fromJson(dto.getParams()).build();
        Session session = openVidu.createSession(properties);
        VideoInterview videoInterviewRoom = VideoInterview.builder()
                .announceUUID(dto.getAnnounceUUID())
                .videoInterviewRoomUUID(session.getSessionId())
                .build();
        videoInterviewRepository.save(videoInterviewRoom);

        return VideoInterviewCreateRes.builder()
                .idx(videoInterviewRoom.getIdx())
                .interviewScheduleRes(dto.getInterviewScheduleInfo())
                .announcementUuid(videoInterviewRoom.getAnnounceUUID())
                .build();
    }

    public String createAll(String announcementUuid, Long announcementIdx) throws OpenViduJavaClientException, OpenViduHttpException, JsonProcessingException {

        List<InterviewSchedule> interviewScheduleList = interviewScheduleRepository.findByAnnouncementIdx(announcementIdx).get();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ObjectMapper objectMapper = new ObjectMapper();


        for(InterviewSchedule interviewSchedule : interviewScheduleList) {
            Map<String, Object> params = new HashMap<>();
            params.put("customSessionId", interviewSchedule.getUuid());

            LocalDateTime interviewDate = LocalDateTime.parse(interviewSchedule.getInterviewDate()+" 00:00:00", formatter);

            // 하루 전 저녁 10시
            LocalDateTime triggerDateTime = interviewDate.minusDays(1).withHour(22).withMinute(0).withSecond(0);


            Date triggerTime = Date.from(triggerDateTime.atZone(ZoneId.systemDefault()).toInstant());

            String jsonParams = objectMapper.writeValueAsString(params);


            // TaskScheduler로 동적 스케줄링
//            taskScheduler.schedule(() -> {
//                try {
//                    SessionProperties properties = SessionProperties.fromJson(jsonParams).build();
//                    Session session = openVidu.createSession(properties);
//
//                    VideoInterview videoInterviewRoom = VideoInterview.builder()
//                            .announceUUID(announcementUuid)
//                            .videoInterviewRoomUUID(session.getSessionId())
//                            .build();
//                    videoInterviewRepository.save(videoInterviewRoom);
//
//                    System.out.println("Video interview room created successfully for UUID: " + interviewSchedule.getUuid());
//                } catch (OpenViduJavaClientException | OpenViduHttpException e) {
//                    e.printStackTrace();
//                }
//            }, triggerTime); // 예약 시간에 생성
        }

        return "Video interviews scheduled for creation!";
    }

    public List<VideoInterviewReadRes> searchAll(String announcementUUID, CustomUserDetails userDetails) throws BaseException {
        if(Objects.equals(userDetails.getRole(), "ROLE_ESTIMATOR")){
            List<InterviewSchedule> interviewScheduleList = interviewScheduleRepository.findByUuidAndEstimatorIdx(announcementUUID, userDetails.getEstimator().getIdx());
            List<VideoInterviewReadRes> videoInterviewReadResList = new ArrayList<>();
            for(InterviewSchedule interviewSchedule : interviewScheduleList){
                VideoInterviewReadRes videoInterviewReadRes = VideoInterviewReadRes.builder()
                        .announcementTitle(interviewSchedule.getAnnouncement().getTitle())
                        .announcementUUID(announcementUUID)
                        .interviewScheduleUUID(interviewSchedule.getUuid())
                        .interviewDate(interviewSchedule.getInterviewDate())
                        .interviewStart(interviewSchedule.getInterviewStart())
                        .interviewEnd(interviewSchedule.getInterviewEnd())
                        .build();
                videoInterviewReadResList.add(videoInterviewReadRes);
            }
            return videoInterviewReadResList;
        } else if (Objects.equals(userDetails.getRole(), "ROLE_SEEKER")) {
            List<InterviewSchedule> interviewScheduleList = interviewScheduleRepository.findByUuidAndSeekerIdx(announcementUUID, userDetails.getSeeker().getIdx());
            List<VideoInterviewReadRes> videoInterviewReadResList = new ArrayList<>();
            for(InterviewSchedule interviewSchedule : interviewScheduleList){
                VideoInterviewReadRes videoInterviewReadRes = VideoInterviewReadRes.builder()
                        .announcementTitle(interviewSchedule.getAnnouncement().getTitle())
                        .announcementUUID(announcementUUID)
                        .interviewScheduleUUID(interviewSchedule.getUuid())
                        .interviewDate(interviewSchedule.getInterviewDate())
                        .interviewStart(interviewSchedule.getInterviewStart())
                        .interviewEnd(interviewSchedule.getInterviewEnd())
                        .build();
                videoInterviewReadResList.add(videoInterviewReadRes);
            }
            return videoInterviewReadResList;
        } else {
            throw new BaseException(BaseResponseMessage.VIDEO_INTERVIEW_SEARCH_ALL_FAIL);
        }
    }

    public VideoInterviewTokenGetRes sessionToken(VideoInterviewTokenGetReq dto, CustomUserDetails userDetails) throws BaseException, OpenViduJavaClientException, OpenViduHttpException {
//        boolean result = checkUserAuthorities(userDetails, dto);
//        if(!result){
//            throw new BaseException(BaseResponseMessage.VIDEO_INTERVIEW_JOIN_FAIL_NOT_TIME);
//        }
        Session session = openVidu.getActiveSession(dto.getVideoInterviewUUID());
        if (session == null) { throw new BaseException(BaseResponseMessage.VIDEO_INTERVIEW_JOIN_FAIL);}
        ConnectionProperties properties = ConnectionProperties.fromJson(dto.getParams()).build();

        try{
            Connection connection = session.createConnection(properties);
            return VideoInterviewTokenGetRes.builder()
                    .sessionToken(connection.getToken())
                    .userEmail(userDetails.getEmail())
                    .userType(userDetails.getRole())
                    .build();
        } catch (Exception e){
            System.out.println("Error creating connection: " + e.getMessage());
            openVidu.createSession(SessionProperties.fromJson(dto.getParams()).build());
            Session activeSession = openVidu.getActiveSession(dto.getVideoInterviewUUID());
            ConnectionProperties reCreateProperties = ConnectionProperties.fromJson(dto.getParams()).build();
            activeSession.getConnection(dto.getVideoInterviewUUID());
            Connection connection = activeSession.createConnection(reCreateProperties);
            return VideoInterviewTokenGetRes.builder()
                    .sessionToken(connection.getToken())
                    .userEmail(userDetails.getEmail())
                    .userType(userDetails.getRole())
                    .build();
        }
    }


    public boolean checkUserAuthorities(CustomUserDetails userDetails, VideoInterviewTokenGetReq dto) {
        // 현재 시스템 시간
        LocalDateTime currentTime = LocalDateTime.now();

        // 권한 스트링 형식: "ROLE_SEEKER|id1|id2|날짜|시작시간|종료시간"
        Collection< ? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        for (GrantedAuthority authority : authorities) {
            String authorityStr = authority.getAuthority();
            if(!Objects.equals(authorityStr, "ROLE_SEEKER") && !Objects.equals(authorityStr, "ROLE_ESTIMATOR")){
                String[] parts = authorityStr.split("\\|");
                String role = parts[0];
                String id1 = parts[1];
                String id2 = parts[2];
                if(parts.length == 6 && Objects.equals(role, "ROLE_SEEKER") && Objects.equals(id1, dto.getAnnounceUUID()) && Objects.equals(id2, dto.getVideoInterviewUUID())){
                    String date = parts[3];
                    String startTime = parts[4];
                    String endTime = parts[5];
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
                    LocalDateTime startDateTime = LocalDateTime.parse(date + " " + startTime, dateFormatter);
                    LocalDateTime endDateTime = LocalDateTime.parse(date + " " + endTime, dateFormatter);
                    LocalDateTime startDateTimeWithBuffer = startDateTime.minusMinutes(3);
                    if (currentTime.isAfter(startDateTimeWithBuffer) && currentTime.isBefore(endDateTime)) {
                        System.out.println("권한이 유효합니다: " + role);
                        return true;
                    }
                }
                if(parts.length == 3 && Objects.equals(role, "ROLE_ESTIMATOR") && Objects.equals(id1, dto.getAnnounceUUID()) && Objects.equals(id2, dto.getVideoInterviewUUID())){
                    return true;
                }
            }
        }
        return false;
    }
}

