package com.example.batch.writer;

import com.example.common.domain.alarm.model.entity.Alarm;
import com.example.common.domain.alarm.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmailWriter implements ItemWriter<List<Alarm>> {

    private final AlarmRepository alarmRepository;

    @Override
    public void write(Chunk<? extends List<Alarm>> chunk) throws Exception {
        // Chunk에서 받은 Alarm 객체들을 저장
        for (List<Alarm> alarmList : chunk.getItems()) {
            for(Alarm alarm : alarmList) {
                alarmRepository.save(alarm);
            }
        }
    }
}