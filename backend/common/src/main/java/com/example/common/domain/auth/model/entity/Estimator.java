package com.example.common.domain.auth.model.entity;


import com.example.common.domain.interview_schedule.model.entity.InterviewParticipate;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Estimator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @Column(nullable = false, length = 100, unique = true)
    private String email; // 이메일
    private String password; // 비밀번호
    private String name;
    private String role; // 접근 권한
    private Boolean emailAuth;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "estimator")
    private List<InterviewParticipate> interviewParticipateList = new ArrayList<>();
}
