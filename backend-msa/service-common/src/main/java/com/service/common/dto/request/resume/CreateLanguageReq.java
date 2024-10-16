package com.service.common.dto.request.resume;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateLanguageReq {
    private String testDiv;
    private String languageName;
    private String conversationLevel;
    private String officialTest;
    private String score;
    private String takingAt;
}
