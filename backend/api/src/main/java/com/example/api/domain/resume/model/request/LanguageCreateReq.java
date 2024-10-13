package com.example.api.domain.resume.model.request;

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
    private String score;
    private String takingAt;
}
