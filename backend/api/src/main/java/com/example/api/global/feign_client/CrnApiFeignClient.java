package com.example.api.global.feign_client;

import com.example.api.domain.auth.model.request.CrnApiReq;
import com.example.api.domain.auth.model.response.CrnApiRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "crnApiFeignClient", url = "http://api.odcloud.kr/api/nts-businessman/v1")
public interface CrnApiFeignClient {

    @PostMapping("/validate")
    CrnApiRes getCrnInfo(@RequestParam("serviceKey") String serviceKey, @RequestBody CrnApiReq crnApiReq);

}
