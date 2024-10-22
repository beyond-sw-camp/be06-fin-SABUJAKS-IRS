package com.example.batch.writer;

import com.example.common.domain.alarm.model.entity.Alarm;
import com.example.common.domain.alarm.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EmailWriter implements ItemWriter<List<Alarm>> {

    private final AlarmRepository alarmRepository;

    @Override
    public void write(Chunk<? extends List<Alarm>> chunk) throws Exception {
        List<Alarm> alarmsToSave = new ArrayList<>();
        for (List<Alarm> alarmList : chunk) {
            alarmsToSave.addAll(alarmList);
        }

        alarmRepository.saveAll(alarmsToSave);
    }
}