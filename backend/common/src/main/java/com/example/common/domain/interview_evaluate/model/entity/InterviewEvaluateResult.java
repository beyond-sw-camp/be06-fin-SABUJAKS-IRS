package com.example.common.domain.interview_evaluate.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    private Integer r1;
    private Integer r2;
    private Integer r3;
    private Integer r4;
    private Integer r5;
    private Integer r6;
    private Integer r7;
    private Integer r8;
    private Integer r9;
    private Integer r10;

    @OneToMany(mappedBy = "interviewEvaluateResult")
    private List<InterviewEvaluate> interviewEvaluateList;
}
