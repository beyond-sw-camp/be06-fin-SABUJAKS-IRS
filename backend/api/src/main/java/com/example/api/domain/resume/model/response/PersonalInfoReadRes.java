package com.example.api.domain.resume.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalInfoReadRes {
    private String name;
    private String birth;
    private String gender;
    private String email;
    private String phone;
    private String tel;
    private String address;
    // 증명사진
    private String profileImg;
}
