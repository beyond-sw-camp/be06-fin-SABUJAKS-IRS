package com.example.api.domain.resume.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LanguageReadRes {
    private String testDiv;
    private String languageName;
    private String conversationLevel;
    private String officialTest;
    private String score;
    private String takingAt;
}
