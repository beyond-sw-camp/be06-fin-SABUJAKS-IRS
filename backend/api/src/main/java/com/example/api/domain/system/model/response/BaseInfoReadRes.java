package com.example.api.domain.system.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseInfoReadRes {
    private String code; // 소분류 코드
    private String description; // 소분류 설명
    private String groupName; // 그룹네임 (부모 description)
    private String parentCode; // 부모 코드 (부모 code)
}
