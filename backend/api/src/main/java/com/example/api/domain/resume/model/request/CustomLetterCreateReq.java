package com.example.api.domain.resume.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomLetterCreateReq {
    private String title;
    private Integer charNum;
    private String contents;
}
