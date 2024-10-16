package com.service.common.dto.request.resume;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCustomLetterReq {
    private String title;
    private Integer charNum;
    private String contents;
}
