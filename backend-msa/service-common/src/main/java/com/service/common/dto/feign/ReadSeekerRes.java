package com.service.common.dto.feign;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadSeekerRes implements ReadMemberRes {
    private Long idx;
    private String name;
    private String nickname;
    private String email;
    private Boolean gender;
    private String birth;
    private String role;
    private String phoneNumber;
    private String address;
    private String socialType;
    private String profileImage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
