package com.service.common.dto.request.member;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginReq {
    private String email;
    private String password;
}
