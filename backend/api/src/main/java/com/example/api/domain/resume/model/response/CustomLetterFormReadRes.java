package com.example.api.domain.resume.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomLetterFormReadRes {
    private String title;
    private Integer chatLimit;
}
