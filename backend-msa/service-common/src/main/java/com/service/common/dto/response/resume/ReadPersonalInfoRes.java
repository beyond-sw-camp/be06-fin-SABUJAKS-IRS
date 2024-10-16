package com.service.common.dto.response.resume;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadPersonalInfoRes {
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
