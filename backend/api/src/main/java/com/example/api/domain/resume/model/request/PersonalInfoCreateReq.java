package com.example.api.domain.resume.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalInfoCreateReq {
    private String name;
    private String birth;
    private String gender;
    private String email;
    private String phone;
    private String tel;
    private String address;
}
