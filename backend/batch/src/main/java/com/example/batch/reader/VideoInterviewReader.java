package com.example.batch.reader;

import com.example.common.domain.announcement.model.entity.Announcement;
import com.example.common.domain.interview_schedule.model.entity.InterviewSchedule;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class VideoInterviewReader {

    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public JpaPagingItemReader<InterviewSchedule> reader() {
        String tomorrow = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));


        return new JpaPagingItemReaderBuilder<InterviewSchedule>()
                .name("interviewScheduleReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT is FROM InterviewSchedule is WHERE is.interviewDate = :tomorrow")
                .parameterValues(Map.of("tomorrow", tomorrow))
//                .parameterValues(Map.of("tomorrow", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
                .pageSize(10)
                .build();
    }
}
