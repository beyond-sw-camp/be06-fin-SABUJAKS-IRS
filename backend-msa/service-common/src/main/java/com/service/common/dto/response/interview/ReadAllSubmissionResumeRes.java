package com.service.common.dto.response.interview;

import com.service.common.dto.feign.ReadSubmissionResumeRes;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadAllSubmissionResumeRes {
    private Map<Long, ReadSubmissionResumeRes> readAllSubmissionResumeResMap;
}
