package com.service.common.dto.response.announcement;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadBaseInfoRes {
    private String code; // 소분류 코드
    private String description; // 소분류 설명
    private String groupName; // 그룹네임 (부모 description)
    private String parentCode; // 부모 코드 (부모 code)
}
