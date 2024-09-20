package com.sabujaks.irs.domain.video_interview.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoInterviewTokenGetRes {
    private String sessionToken;
    private String userEmail;
    private String userType;
}
