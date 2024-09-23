package com.sabujaks.irs.domain.auth.model.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RecruiterRes {
    private Long idx;
    private String name;
    private String email;
}
