package com.service.interview.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.common.base.BaseException;
import com.service.common.base.BaseResponseMessage;
import com.service.common.dto.feign.ReadAnnouncementRes;
import com.service.common.dto.request.interview.CreateAllInterviewOnlineReq;
import com.service.interview.communication.InterviewFeignClient;
import com.service.interview.entity.InterviewOnline;
import com.service.interview.entity.InterviewSchedule;
import com.service.common.dto.request.interview.CreateInterviewOnlineReq;
import com.service.common.dto.request.interview.ReadInterviewOlineTokenReq;
import com.service.common.dto.response.interview.CreateInterviewOnlineRes;
import com.service.common.dto.response.interview.ReadInterviewOnlineRes;
import com.service.common.dto.response.interview.ReadInterviewOnlineTokenRes;
import com.service.interview.repository.InterviewOnlineRepository;
import com.service.interview.repository.InterviewScheduleRepository;
import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class InterviewOnlineService {
    private final OpenVidu openVidu;
    private final InterviewOnlineRepository interviewOnlineRepository;
    private final InterviewScheduleRepository interviewScheduleRepository;
    private final InterviewFeignClient interviewFeignClient;
//    private final TaskScheduler taskScheduler;

    public CreateInterviewOnlineRes create(CreateInterviewOnlineReq dto) throws OpenViduJavaClientException, OpenViduHttpException, BaseException {
        SessionProperties properties = SessionProperties.fromJson(dto.getParams()).build();
        Session session = openVidu.createSession(properties);
        InterviewOnline interviewOnline = InterviewOnline.builder()
                .announceUUID(dto.getAnnounceUUID())
                .videoInterviewRoomUUID(session.getSessionId())
                .build();
        interviewOnlineRepository.save(interviewOnline);
        return CreateInterviewOnlineRes.builder()
                .idx(interviewOnline.getIdx())
                .interviewScheduleRes(dto.getReadInterviewScheduleRes())
                .announcementUuid(interviewOnline.getAnnounceUUID())
                .build();
    }
    // 배치
    public String createAll(CreateAllInterviewOnlineReq dto) throws OpenViduJavaClientException, OpenViduHttpException, JsonProcessingException {
        List<InterviewSchedule> interviewScheduleList = interviewScheduleRepository.findByAnnouncementIdx(dto.getAnnouncementIdx()).get();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ObjectMapper objectMapper = new ObjectMapper();
        for(InterviewSchedule interviewSchedule : interviewScheduleList) {
            Map<String, Object> params = new HashMap<>();
            params.put("customSessionId", interviewSchedule.getInterviewScheduleUUID());
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

    public List<ReadInterviewOnlineRes> readAll(Long memberIdx, String memberEmail, String memberRole, String announcementUUID) throws BaseException {
        if(Objects.equals(memberRole, "ROLE_ESTIMATOR")){
            List<InterviewSchedule> interviewScheduleList = interviewScheduleRepository.findByAnnouncementUUIDAndEstimatorEmail(announcementUUID, memberEmail);
            List<ReadInterviewOnlineRes> readInterviewOnlineResList = new ArrayList<>();
            for(InterviewSchedule interviewSchedule : interviewScheduleList){
                ReadAnnouncementRes readAnnouncementRes = interviewFeignClient.readAnnouncement(interviewSchedule.getAnnouncementIdx());
                ReadInterviewOnlineRes readInterviewOnlineRes = ReadInterviewOnlineRes.builder()
                        .announcementTitle(readAnnouncementRes.getAnnouncementTitle())
                        .announcementUUID(announcementUUID)
                        .interviewScheduleUUID(interviewSchedule.getInterviewScheduleUUID())
                        .interviewDate(interviewSchedule.getInterviewDate())
                        .interviewStart(interviewSchedule.getInterviewStart())
                        .interviewEnd(interviewSchedule.getInterviewEnd())
                        .build();
                readInterviewOnlineResList.add(readInterviewOnlineRes);
            }
            return readInterviewOnlineResList;
        } else if (Objects.equals(memberRole, "ROLE_SEEKER")) {
            List<InterviewSchedule> interviewScheduleList = interviewScheduleRepository.findByAnnouncementUUIDAndSeekerIdx(announcementUUID, memberIdx);
            List<ReadInterviewOnlineRes> readInterviewOnlineResList = new ArrayList<>();
            for(InterviewSchedule interviewSchedule : interviewScheduleList){
                ReadAnnouncementRes readAnnouncementRes = interviewFeignClient.readAnnouncement(interviewSchedule.getAnnouncementIdx());
                ReadInterviewOnlineRes readInterviewOnlineRes = ReadInterviewOnlineRes.builder()
                        .announcementTitle(readAnnouncementRes.getAnnouncementTitle())
                        .announcementUUID(announcementUUID)
                        .interviewScheduleUUID(interviewSchedule.getInterviewScheduleUUID())
                        .interviewDate(interviewSchedule.getInterviewDate())
                        .interviewStart(interviewSchedule.getInterviewStart())
                        .interviewEnd(interviewSchedule.getInterviewEnd())
                        .build();
                readInterviewOnlineResList.add(readInterviewOnlineRes);
            }
            return readInterviewOnlineResList;
        } else {
            throw new BaseException(BaseResponseMessage.VIDEO_INTERVIEW_SEARCH_ALL_FAIL);
        }
    }

    public ReadInterviewOnlineTokenRes readOnlineToken(Long memberIdx, String memberEmail, String memberRole, ReadInterviewOlineTokenReq dto) throws BaseException, OpenViduJavaClientException, OpenViduHttpException {
    // TODO: 시간별 접근 제어, POD UTC -> 한국시간으로 변경해야됨 / 세션관리
        //        boolean result = checkUserAuthorities(userDetails, dto);
//        if(!result){
//            throw new BaseException(BaseResponseMessage.VIDEO_INTERVIEW_JOIN_FAIL_NOT_TIME);
//        }
        Session session = openVidu.getActiveSession(dto.getAnnounceUUID());
        if (session == null) { throw new BaseException(BaseResponseMessage.VIDEO_INTERVIEW_JOIN_FAIL);}
        ConnectionProperties properties = ConnectionProperties.fromJson(dto.getParams()).build();

        try{
            Connection connection = session.createConnection(properties);
            return ReadInterviewOnlineTokenRes.builder()
                    .sessionToken(connection.getToken())
                    .userEmail(memberEmail)
                    .userType(memberRole)
                    .build();
        } catch (Exception e){
            System.out.println("Error creating connection: " + e.getMessage());
            openVidu.createSession(SessionProperties.fromJson(dto.getParams()).build());
            Session activeSession = openVidu.getActiveSession(dto.getVideoInterviewUUID());
            ConnectionProperties reCreateProperties = ConnectionProperties.fromJson(dto.getParams()).build();
            activeSession.getConnection(dto.getVideoInterviewUUID());
            Connection connection = activeSession.createConnection(reCreateProperties);
            return ReadInterviewOnlineTokenRes.builder()
                    .sessionToken(connection.getToken())
                    .userEmail(memberEmail)
                    .userType(memberRole)
                    .build();
        }
    }

//    public boolean checkUserAuthorities(CustomUserDetails userDetails, VideoInterviewTokenGetReq dto) {
//        // 현재 시스템 시간
//        LocalDateTime currentTime = LocalDateTime.now();
//
//        // 권한 스트링 형식: "ROLE_SEEKER|id1|id2|날짜|시작시간|종료시간"
//        Collection< ? extends GrantedAuthority> authorities = userDetails.getAuthorities();
//
//        for (GrantedAuthority authority : authorities) {
//            String authorityStr = authority.getAuthority();
//            if(!Objects.equals(authorityStr, "ROLE_SEEKER") && !Objects.equals(authorityStr, "ROLE_ESTIMATOR")){
//                String[] parts = authorityStr.split("\\|");
//                String role = parts[0];
//                String id1 = parts[1];
//                String id2 = parts[2];
//                if(parts.length == 6 && Objects.equals(role, "ROLE_SEEKER") && Objects.equals(id1, dto.getAnnounceUUID()) && Objects.equals(id2, dto.getVideoInterviewUUID())){
//                    String date = parts[3];
//                    String startTime = parts[4];
//                    String endTime = parts[5];
//                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
//                    LocalDateTime startDateTime = LocalDateTime.parse(date + " " + startTime, dateFormatter);
//                    LocalDateTime endDateTime = LocalDateTime.parse(date + " " + endTime, dateFormatter);
//                    LocalDateTime startDateTimeWithBuffer = startDateTime.minusMinutes(3);
//                    if (currentTime.isAfter(startDateTimeWithBuffer) && currentTime.isBefore(endDateTime)) {
//                        System.out.println("권한이 유효합니다: " + role);
//                        return true;
//                    }
//                }
//                if(parts.length == 3 && Objects.equals(role, "ROLE_ESTIMATOR") && Objects.equals(id1, dto.getAnnounceUUID()) && Objects.equals(id2, dto.getVideoInterviewUUID())){
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
}

