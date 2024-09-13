package com.sabujaks.irs.domain.interview_schedule.model.entity;


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
    private String email; // 이메일
    private String password; // 비밀번호
    private String role; // 접근 권한
    private Boolean emailAuth;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "estimator")
    private List<InterviewParticipate> interviewParticipateList = new ArrayList<>();
//    @ManyToOne
//    @JoinColumn(name = "team_idx")
//    private Team team;
}