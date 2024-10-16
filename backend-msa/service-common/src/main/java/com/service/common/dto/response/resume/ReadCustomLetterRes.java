package com.service.common.dto.response.resume;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadCustomLetterRes {
    private String title;
    private Integer charNum;
    private String contents;
}
