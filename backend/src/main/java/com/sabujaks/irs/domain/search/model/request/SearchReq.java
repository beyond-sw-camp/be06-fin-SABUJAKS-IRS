package com.sabujaks.irs.domain.search.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchReq {
    private Integer page;
    private Integer size;
    private String keyword;
    private String careerBase;
    private String jobCategory;
    private String region;
    private String sort;
}
