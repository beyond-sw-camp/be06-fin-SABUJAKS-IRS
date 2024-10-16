package com.service.common.dto.feign;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidateCrnApiReq {

    private List<Business> businesses;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Business {
        private String b_no;
        private String start_dt;
        private String p_nm;
    }
}
