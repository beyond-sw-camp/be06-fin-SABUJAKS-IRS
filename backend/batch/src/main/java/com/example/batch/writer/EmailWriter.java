package com.example.batch.writer;

import com.example.common.domain.alarm.model.entity.Alarm;
import com.example.common.domain.alarm.repository.AlarmRepository;
import com.example.common.domain.auth.repository.SeekerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmailWriter implements ItemWriter<Alarm> {

    private final AlarmRepository alarmRepository;

    @Override
    public void write(Chunk<? extends Alarm> alarms) throws Exception {
        alarmRepository.saveAll(alarms);
    }
}
