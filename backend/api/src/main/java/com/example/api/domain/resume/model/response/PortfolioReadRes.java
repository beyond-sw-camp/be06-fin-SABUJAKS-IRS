package com.example.api.domain.resume.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioReadRes {
    private String portfolioDiv;
    private String portfolioType;
    private String portfolioUrl;
}
