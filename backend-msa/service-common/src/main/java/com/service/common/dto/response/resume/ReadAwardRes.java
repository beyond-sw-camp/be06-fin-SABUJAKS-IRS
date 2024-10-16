package com.service.common.dto.response.resume;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadAwardRes {
    private String awardName;
    private String contents;
    private String organization;
    private String year;
}
