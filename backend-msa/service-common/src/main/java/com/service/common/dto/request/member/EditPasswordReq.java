package com.service.common.dto.request.member;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditPasswordReq {
    private String originPassword;
    private String newPassword;
}
