package com.example.api.domain.auth.model.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrnApiRes {

    private int request_cnt;
    private String status_code;
    private List<DataInfo> data;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DataInfo {
        private String b_no;
        private String valid;
        private String valid_msg;
    }
}

