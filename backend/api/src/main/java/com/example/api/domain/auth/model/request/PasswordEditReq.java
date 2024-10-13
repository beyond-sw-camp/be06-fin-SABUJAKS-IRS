package com.example.api.domain.auth.model.request;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordEditReq {
    private String originPassword;
    private String newPassword;
}
