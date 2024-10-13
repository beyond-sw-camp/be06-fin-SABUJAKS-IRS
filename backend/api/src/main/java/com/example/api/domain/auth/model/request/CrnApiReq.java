package com.example.api.domain.auth.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrnApiReq {
    private List<Business> businesses;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Business {
        private String b_no;      // 사업자 번호 ("b_no"에 매핑)
        private String start_dt;  // 시작 날짜 ("start_dt"에 매핑)
        private String p_nm;      // 대표자 이름 ("p_nm"에 매핑)
    }
}

