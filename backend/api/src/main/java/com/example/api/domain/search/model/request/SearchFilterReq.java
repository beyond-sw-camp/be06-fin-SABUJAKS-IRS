package com.example.api.domain.search.model.request;

import com.example.api.domain.search.model.request.FilterDto;
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
