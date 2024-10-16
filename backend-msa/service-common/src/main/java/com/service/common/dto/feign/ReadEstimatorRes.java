package com.service.common.dto.feign;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ReadEstimatorRes implements ReadMemberRes {

    private Long idx;
    private String email;
    private String name;
    private String role;
    private Boolean isEmailAuth;
}
