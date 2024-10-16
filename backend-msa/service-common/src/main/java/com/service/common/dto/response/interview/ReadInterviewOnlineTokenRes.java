package com.service.common.dto.response.interview;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadInterviewOnlineTokenRes {
    private String sessionToken;
    private String userEmail;
    private String userType;
}
