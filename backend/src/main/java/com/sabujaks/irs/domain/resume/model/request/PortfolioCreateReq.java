package com.sabujaks.irs.domain.resume.model.request;

import jakarta.persistence.Column;
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
