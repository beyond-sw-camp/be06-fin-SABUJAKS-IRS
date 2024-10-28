package com.example.api.domain.alarm.service;

import com.example.api.domain.alarm.model.request.AlarmReq;
import com.example.api.domain.alarm.model.response.AlarmRes;
import com.example.api.global.common.exception.BaseException;
import com.example.api.global.common.responses.BaseResponseMessage;
import com.example.common.domain.alarm.model.entity.Alarm;
import com.example.common.domain.alarm.repository.AlarmRepository;
import com.example.common.domain.interview_schedule.repository.InterviewScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final InterviewScheduleRepository interviewScheduleRepository;

    private final static Long DEFAULT_TIMEOUT = 3600000L;
    private final static String NOTIFICATION_NAME = "notify";

    public AlarmService(AlarmRepository alarmRepository, InterviewScheduleRepository interviewScheduleRepository) {
        this.alarmRepository = alarmRepository;
        this.interviewScheduleRepository = interviewScheduleRepository;
    }


    public AlarmRes create(AlarmReq dto, Long seekerIdx) throws BaseException {
        Alarm alarm = alarmRepository.save(Alarm.builder()
                .type(dto.getType())
                .status(dto.getStatus())
                .message(dto.getMessage())
                .build());

        // 알람 전송
//        sendAlarm(seekerIdx, "새로운 알림이 도착했습니다.");

        return AlarmRes.builder()
                .idx(alarm.getIdx())
                .type(alarm.getType())
                .status(alarm.getStatus())
                .message(alarm.getMessage())
                .createdAt(alarm.getCreatedAt())
                .build();
    }

    public List<AlarmRes> readAll(Long idx) throws BaseException {

        List<Alarm> result = alarmRepository.findBySeekerIdx(idx)
                                .orElseThrow(() -> new BaseException(BaseResponseMessage.ALARM_SEARCH_FAIL));
        List<AlarmRes> alarmResult = new ArrayList<>();
        for (Alarm alarm : result) {
            AlarmRes.AlarmResBuilder alarmResBuilder = AlarmRes.builder()
                    .idx(alarm.getIdx())
                    .type(alarm.getType())
                    .status(alarm.getStatus())
                    .message(alarm.getMessage())
                    .createdAt(alarm.getCreatedAt());

            // alarm.getInterviewSchedule()이 null이 아니면 interviewScheduleIdx 설정
            if (alarm.getInterviewSchedule() != null) {
                alarmResBuilder.interviewScheduleIdx(alarm.getInterviewSchedule().getIdx());
            }

            alarmResult.add(alarmResBuilder.build());
        }

        return alarmResult;
    }

    public Boolean updateStatus(Long idx) throws BaseException {
        Alarm alarm = alarmRepository.findById(idx)
                        .orElseThrow(() -> new BaseException(BaseResponseMessage.ALARM_SEARCH_FAIL));

        alarm.setStatus(true);
        alarmRepository.save(alarm);

        return true;
    }

    // 클라이언트별로 SseEmitter를 저장하는 Map
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter subscribe(Long seekerIdx) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitters.put(seekerIdx, emitter);

        // 연결이 종료될 때 emitters에서 제거
        emitter.onCompletion(() -> emitters.remove(seekerIdx));
        emitter.onTimeout(() -> emitters.remove(seekerIdx));

        // 주기적으로 데이터를 보내기 위한 스레드 생성
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {
                // 데이터를 전송
                emitter.send(SseEmitter.event().data("keep-alive")); // 빈 데이터 전송
            } catch (IOException e) {
                // IOException 발생 시 emitter를 제거
                emitters.remove(seekerIdx);
                // 로그를 남기거나 적절한 예외 처리를 추가할 수 있습니다.
                System.out.println("Emitter closed: " + e.getMessage());
            }
        }, 0, 10, TimeUnit.SECONDS); // 10초마다 전송

        return emitter;
    }

    public void sendAlarm(Long seekerIdx, String message) {
        SseEmitter emitter = emitters.get(seekerIdx);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@");
        System.out.println(emitter);
        System.out.println(seekerIdx);
        System.out.println(message);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@");
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name("alarm")
                        .data(message));
            } catch (IOException e) {
                emitters.remove(seekerIdx);
            }
        }
    }
}
