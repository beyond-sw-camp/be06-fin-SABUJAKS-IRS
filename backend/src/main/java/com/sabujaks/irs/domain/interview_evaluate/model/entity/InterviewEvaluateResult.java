package com.sabujaks.irs.domain.interview_evaluate.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewEvaluateResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String r1;
    private String r2;
    private String r3;
    private String r4;
    private String r5;
    private String r6;
    private String r7;
    private String r8;
    private String r9;
    private String r10;

    @ManyToOne
    @JoinColumn(name = "interviewEvaluate_idx")
    private InterviewEvaluate interviewEvaluate;
}
