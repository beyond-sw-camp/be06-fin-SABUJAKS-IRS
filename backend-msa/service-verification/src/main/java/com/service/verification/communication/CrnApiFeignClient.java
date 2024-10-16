package com.service.verification.communication;

import com.service.common.dto.feign.ValidateCrnApiReq;
import com.service.common.dto.feign.ValidateCrnApiRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "crnApiFeignClient", url = "http://api.odcloud.kr/api/nts-businessman/v1")
public interface CrnApiFeignClient {

    @PostMapping("/validate")
    ValidateCrnApiRes getCrnInfo(@RequestParam("serviceKey") String serviceKey, @RequestBody ValidateCrnApiReq validateCrnApiReq);
}
