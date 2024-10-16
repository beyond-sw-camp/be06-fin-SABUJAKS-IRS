package com.service.common.dto.request.resume;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePortfolioReq {
    private String portfolioDiv;
    private String portfolioType;
    private String portfolioUrl;
}
