package com.service.interview.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewEvaluateForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private Long announcementIdx;

    private String announcementUUID;

    private String q1;

    private String q2;

    private String q3;

    private String q4;

    private String q5;

    private String q6;

    private String q7;

    private String q8;

    private String q9;

    private String q10;

    @OneToMany(mappedBy = "interviewEvaluateForm")
    private List<InterviewEvaluate> interviewEvaluateList;
 }
