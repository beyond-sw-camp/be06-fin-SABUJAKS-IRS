package com.sabujaks.irs.domain.alarm.service;

import com.sabujaks.irs.domain.alarm.model.entity.Alarm;
import com.sabujaks.irs.domain.alarm.model.request.AlarmReq;
import com.sabujaks.irs.domain.alarm.model.response.AlarmRes;
import com.sabujaks.irs.domain.alarm.repository.AlarmRepository;
import com.sabujaks.irs.domain.interview_schedule.repository.InterviewScheduleRepository;
import com.sabujaks.irs.global.common.exception.BaseException;
import com.sabujaks.irs.global.common.responses.BaseResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final InterviewScheduleRepository interviewScheduleRepository;

    public AlarmRes create(AlarmReq dto) throws BaseException {
        Alarm alarm = alarmRepository.save(Alarm.builder()
                .type(dto.getType())
                .status(dto.getStatus())
                .message(dto.getMessage())
                .build());

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
        for(Alarm alarm : result) {
            alarmResult.add(AlarmRes.builder()
                    .idx(alarm.getIdx())
                    .type(alarm.getType())
                    .status(alarm.getStatus())
                    .message(alarm.getMessage())
                    .createdAt(alarm.getCreatedAt())
                    .interviewScheduleIdx(alarm.getInterviewSchedule().getIdx())
                    .build());
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
}
