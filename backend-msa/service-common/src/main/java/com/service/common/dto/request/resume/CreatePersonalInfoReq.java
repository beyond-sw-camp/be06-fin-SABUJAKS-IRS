package com.service.common.dto.request.resume;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePersonalInfoReq {
    private String name;
    private String birth;
    private String gender;
    private String email;
    private String phone;
    private String tel;
    private String address;
}
