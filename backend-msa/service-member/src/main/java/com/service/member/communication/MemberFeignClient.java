package com.service.member.communication;

import com.service.common.dto.feign.ReadVerificationRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "memberFeignClient", url = "http://localhost:8080")
public interface MemberFeignClient {

    // TODO: 컨트롤러 보안 설정 auth 서비스가 실행되는 IP에 한해서만 내부 접근을 허용해야 한다.
    @GetMapping("/api/verification/read")
    ReadVerificationRes readVerification(@RequestParam("email") String email, @RequestParam("flag") Boolean flag);
}


