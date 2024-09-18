package com.sabujaks.irs.domain.resume.model.request;

import jakarta.persistence.Column;
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
