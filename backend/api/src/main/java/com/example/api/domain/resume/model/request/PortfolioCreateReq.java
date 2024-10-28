package com.example.api.domain.resume.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioCreateReq {
    private String portfolioDiv;
    private String portfolioType;
    private String portfolioUrl;
}
