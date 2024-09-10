package com.sabujaks.irs.domain.video_interview.mdoel.entity;

import jakarta.persistence.*;
import lombok.*;

// 화상 면접방 엔티티
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoInterview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String announceUUID;
    private String videoInterviewRoomUUID;
    // 위 엔티티의 UUID들은 다른테이블에서 연관관계로 매핑할 예정
//    @OneToMany(mappedBy = "videoInterviewRoom")
//    @JsonManagedReference
//    private List<InterviewSchedule> interviewScheduleList = new ArrayList<>();
}
