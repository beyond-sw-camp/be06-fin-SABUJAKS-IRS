package com.example.api.global.security;

import com.example.api.domain.video_interview.model.request.VideoInterviewRequest;
import com.example.api.domain.video_interview.model.request.VideoInterviewTokenGetReq;
import com.example.common.domain.announcement.model.entity.Announcement;
import com.example.common.domain.auth.model.entity.Estimator;
import com.example.common.domain.auth.model.entity.Recruiter;
import com.example.common.domain.auth.model.entity.Seeker;
import com.example.common.domain.auth.repository.EstimatorRepository;
import com.example.common.domain.auth.repository.RecruiterRepository;
import com.example.common.domain.auth.repository.SeekerRepository;
import com.example.common.domain.interview_schedule.model.entity.InterviewParticipate;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class AccessControlService {
    private final SeekerRepository seekerRepository;
    private final RecruiterRepository recruiterRepository;
    private final EstimatorRepository estimatorRepository;
    private final ObjectMapper objectMapper;
    public Optional<String> extractRole(Supplier<Authentication> authentication) {
        return authentication.get().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst();
    }

    public AuthorizationDecision hasVideoInterviewAccessAuthorities(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        String announcementUUID = object.getRequest().getParameter("announcementUUID");
        String videoInterviewUUID = object.getRequest().getParameter("videoInterviewUUID");
        String seekerAuthority = "ROLE_SEEKER|" +announcementUUID + '|' +videoInterviewUUID;
        String estimatorAuthority = "ROLE_ESTIMATOR|" + announcementUUID + '|' +videoInterviewUUID;
        String recruiterAuthority = "ROLE_RECRUITER|" + announcementUUID;
        Optional<String> extractRole = extractRole(authentication);
        if(extractRole.isPresent()){
            if (Objects.equals(extractRole.get(), "ROLE_SEEKER")) {
                Optional<Seeker> resultSeeker = seekerRepository.findBySeekerEmail(authentication.get().getName());
                if(resultSeeker.isPresent() && resultSeeker.get().getInterviewParticipateList() != null){
                    Seeker seeker = resultSeeker.get();
                    for (InterviewParticipate participate : seeker.getInterviewParticipateList()) {
                        String authority =
                                "ROLE_SEEKER|" + participate.getInterviewSchedule().getAnnouncement().getUuid()
                                        + "|" + participate.getInterviewSchedule().getUuid()
                                        + "|" + participate.getInterviewSchedule().getInterviewDate()
                                        + "|" + participate.getInterviewSchedule().getInterviewStart()
                                        + "|" + participate.getInterviewSchedule().getInterviewEnd();
                        if(authority.split("\\|")[1].equals(object.getRequest().getParameter("announcementUUID")) || authority.contains(seekerAuthority)){
                            return new AuthorizationDecision(true);
                        }
                    }
                }
            }
            if (Objects.equals(extractRole.get(), "ROLE_ESTIMATOR")) {
                Optional<Estimator> resultEstimator = estimatorRepository.findByEstimatorEmail(authentication.get().getName());
                if(resultEstimator.isPresent() && resultEstimator.get().getInterviewParticipateList() != null){
                    Estimator estimator = resultEstimator.get();
                    for (InterviewParticipate participate : estimator.getInterviewParticipateList()) {
                        String authority =
                                "ROLE_ESTIMATOR|" + participate.getInterviewSchedule().getAnnouncement().getUuid()
                                        + "|" + participate.getInterviewSchedule().getUuid();
                        if(authority.split("\\|")[1].equals(object.getRequest().getParameter("announcementUUID")) || authority.contains(estimatorAuthority)){
                            return new AuthorizationDecision(true);
                        }
                    }
                }
            }
            if (Objects.equals(extractRole.get(), "ROLE_RECRUITER")) {
                Optional<Recruiter> resultRecruiter = recruiterRepository.findByRecruiterEmail(authentication.get().getName());
                if(resultRecruiter.isPresent() && resultRecruiter.get().getAnnouncementList() != null){
                    Recruiter recruiter = resultRecruiter.get();
                    for (Announcement announcement : recruiter.getAnnouncementList()) {
                        String authority = "ROLE_RECRUITER|" + announcement.getUuid();
                        if(authority.split("\\|")[1].equals(object.getRequest().getParameter("announcementUUID")) || authority.contains(recruiterAuthority)){
                            return new AuthorizationDecision(true);
                        }
                    }
                }
            }
        }
        return new AuthorizationDecision(false);
    }

    public AuthorizationDecision hasVideoInterviewAccessTimeAuthorities(Supplier<Authentication> authentication, RequestAuthorizationContext object) {

        VideoInterviewTokenGetReq request = null;
        try{
            request = objectMapper.readValue(object.getRequest().getInputStream(), VideoInterviewTokenGetReq.class);
            System.out.println("Received request: " + request);
        } catch (IOException e){
            return new AuthorizationDecision(false);
        }
        String announcementUUID = request.getAnnounceUUID();
        String videoInterviewUUID = request.getVideoInterviewUUID();
        String seekerAuthority = "ROLE_SEEKER|" + announcementUUID + '|' + videoInterviewUUID;
        String estimatorAuthority = "ROLE_SEEKER|" + announcementUUID + '|' + videoInterviewUUID;
        Optional<String> extractRole = extractRole(authentication);
        if (extractRole.isPresent()) {
            if (Objects.equals(extractRole.get(), "ROLE_SEEKER")) {
                Optional<Seeker> resultSeeker = seekerRepository.findBySeekerEmail(authentication.get().getName());
                if (resultSeeker.isPresent() && resultSeeker.get().getInterviewParticipateList() != null) {
                    Seeker seeker = resultSeeker.get();
                    for (InterviewParticipate participate : seeker.getInterviewParticipateList()) {
                        String authority =
                                "ROLE_SEEKER|" + participate.getInterviewSchedule().getAnnouncement().getUuid()
                                        + "|" + participate.getInterviewSchedule().getUuid()
                                        + "|" + participate.getInterviewSchedule().getInterviewDate()
                                        + "|" + participate.getInterviewSchedule().getInterviewStart()
                                        + "|" + participate.getInterviewSchedule().getInterviewEnd();
                        if (authority.split("\\|")[1].equals(object.getRequest().getParameter("announcementUUID")) || authority.contains(seekerAuthority)) {
                            if(checkTime(extractRole.get(), authority, announcementUUID, videoInterviewUUID)){
                                return new AuthorizationDecision(true);
                            }
                        }
                    }
                }
            }
            if (Objects.equals(extractRole.get(), "ROLE_ESTIMATOR")) {
                Optional<Estimator> resultEstimator = estimatorRepository.findByEstimatorEmail(authentication.get().getName());
                if (resultEstimator.isPresent() && resultEstimator.get().getInterviewParticipateList() != null) {
                    Estimator estimator = resultEstimator.get();
                    for (InterviewParticipate participate : estimator.getInterviewParticipateList()) {
                        String authority =
                                "ROLE_ESTIMATOR|" + participate.getInterviewSchedule().getAnnouncement().getUuid()
                                        + "|" + participate.getInterviewSchedule().getUuid();
                        if (authority.split("\\|")[1].equals(object.getRequest().getParameter("announcementUUID")) || authority.contains(estimatorAuthority)) {
                            checkTime(extractRole.get(), authority, announcementUUID, videoInterviewUUID);
                            return new AuthorizationDecision(true);
                        }
                    }
                }
            }
        }
        return new AuthorizationDecision(false);
    }

    public boolean checkTime(String role, String authority, String announcementUUID, String videoInterviewUUID){
        LocalDateTime currentTime = LocalDateTime.now();
        // 권한 스트링 형식: "ROLE_SEEKER|id1|id2|날짜|시작시간|종료시간"
        if(Objects.equals(role, "ROLE_SEEKER") || Objects.equals(role, "ROLE_ESTIMATOR")){
                String[] parts = authority.split("\\|");
                String id1 = parts[1];
                String id2 = parts[2];
                if(parts.length == 6 && Objects.equals(role, "ROLE_SEEKER") && Objects.equals(id1, announcementUUID) && Objects.equals(id2,videoInterviewUUID)){
                    String date = parts[3];
                    String startTime = parts[4];
                    String endTime = parts[5];
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    LocalDateTime startDateTime = LocalDateTime.parse(date + " " + startTime, dateFormatter);
                    LocalDateTime endDateTime = LocalDateTime.parse(date + " " + endTime, dateFormatter);
                    LocalDateTime startDateTimeWithBuffer = startDateTime.minusMinutes(3);
                    if (currentTime.isAfter(startDateTimeWithBuffer) && currentTime.isBefore(endDateTime)) {
                        return true;
                    }
                }
                if(parts.length == 3 && Objects.equals(role, "ROLE_ESTIMATOR") && Objects.equals(id1, announcementUUID) && Objects.equals(id2, videoInterviewUUID)){
                    return true;
                }
            }
        return false;
    }
}
