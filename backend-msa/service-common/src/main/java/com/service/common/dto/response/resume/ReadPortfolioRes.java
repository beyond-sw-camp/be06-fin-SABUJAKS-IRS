package com.service.common.dto.response.resume;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadPortfolioRes {
    private String portfolioDiv;
    private String portfolioType;
    private String portfolioUrl;
}
