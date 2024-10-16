package com.service.common.dto.request.resume;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAwardReq {
    private String awardName;
    private String contents;
    private String organization;
    private String year;
}
