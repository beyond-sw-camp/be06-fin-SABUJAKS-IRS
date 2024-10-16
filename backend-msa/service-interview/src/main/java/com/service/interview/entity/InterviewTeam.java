package com.service.interview.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String teamName;

    @OneToMany(mappedBy = "interviewTeam")
    private List<InterviewParticipate> interviewParticipateList;

    @OneToMany(mappedBy = "interviewTeam")
    private List<InterviewSchedule> interviewScheduleList;
}
