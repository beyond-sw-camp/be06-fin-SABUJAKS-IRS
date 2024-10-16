package com.service.common.dto.response.resume;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadLanguageRes {
    private String testDiv;
    private String languageName;
    private String conversationLevel;
    private String officialTest;
    private String score;
    private String takingAt;
}
