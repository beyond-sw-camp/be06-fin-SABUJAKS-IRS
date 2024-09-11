package com.sabujaks.irs.domain.resume.model.request;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LanguageCreateReq {
    private String testDiv;
    private String languageName;
    private String conversationLevel;
    private String officialTest;
    private Double score;
    private String takingAt;
}
