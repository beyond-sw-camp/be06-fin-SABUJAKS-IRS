package com.sabujaks.irs.domain.video_interview.model.entity;

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

}
