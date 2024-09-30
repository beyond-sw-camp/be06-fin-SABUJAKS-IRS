package com.sabujaks.irs.domain.search.model.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchFilterReq {
    private List<FilterDto> filters;  // JSON 객체로 들어온 필터 배열
}
