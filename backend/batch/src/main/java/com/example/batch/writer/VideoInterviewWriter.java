package com.example.batch.writer;

import com.example.common.domain.video_interview.model.entity.VideoInterview;
import com.example.common.domain.video_interview.repository.VideoInterviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoInterviewWriter implements ItemWriter<VideoInterview> {
    private final VideoInterviewRepository videoInterviewRepository;

    @Override
    public void write(Chunk<? extends VideoInterview> videoInterviews) throws Exception {
         videoInterviewRepository.saveAll(videoInterviews);
    }
}