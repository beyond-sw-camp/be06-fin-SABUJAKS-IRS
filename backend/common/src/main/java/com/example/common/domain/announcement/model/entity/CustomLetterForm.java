package com.example.common.domain.announcement.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomLetterForm { //자기소개서 맞춤 양식
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private String title; // 자기소개서 문항

    private Integer chatLimit; // 글자수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "announcement_idx")
    private Announcement announcement;

}
