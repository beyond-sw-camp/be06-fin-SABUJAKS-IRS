package com.sabujaks.irs.domain.resume.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomLetterReadRes {
    private String title;
    private Integer charNum;
    private String contents;
}
