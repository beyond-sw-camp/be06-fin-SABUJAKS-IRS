package com.service.interview.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// 화상 면접방 엔티티
public class InterviewOnline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String announceUUID;

    private String videoInterviewRoomUUID;
}
