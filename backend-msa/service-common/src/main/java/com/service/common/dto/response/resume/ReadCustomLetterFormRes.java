package com.service.common.dto.response.resume;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadCustomLetterFormRes {
    private String title;
    private Integer chatLimit;
}
