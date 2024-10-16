//package com.example.batch.processor;
//
//import com.example.common.domain.alarm.model.entity.Alarm;
//import com.example.common.domain.announcement.model.entity.Announcement;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//
//@Component
//@RequiredArgsConstructor
//public class EmailFinalResultProcessor implements ItemProcessor<Announcement, List<Alarm>> {
//
//    @Override
//    public List<Alarm> process(Announcement announcement) throws Exception {
////        Optional<Announcement> announcement = announcementRepository.findByAnnounceIdx(interviewSchedule.getAnnouncement().getIdx());
////
////        if(announcement.isPresent()) {
////            Map<String, Object> params = new HashMap<>();
////            params.put("customSessionId", interviewSchedule.getUuid());
////
////            SessionProperties properties = SessionProperties.fromJson(params).build();
////            Session session = openVidu.createSession(properties);
//
//            return Alarm.builder()
//                    .build();
//        } else {
//            return null;
//        }
//    }
//}