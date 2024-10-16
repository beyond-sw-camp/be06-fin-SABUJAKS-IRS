package com.service.common.dto.feign;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadRecruiterRes implements ReadMemberRes {
    private Long idx;
    private String name;
    private String email;
    private String role;
    private String phoneNumber;
}
