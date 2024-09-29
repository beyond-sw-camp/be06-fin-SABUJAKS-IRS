package com.sabujaks.irs.domain.auth.model.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeekerRes {
    private String email;
    private String name;
    private String nickname;
    private Boolean gender;
    private String birth;
    private String phoneNumber;
    private String address;
    private String socialType;
    private String profileImage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
