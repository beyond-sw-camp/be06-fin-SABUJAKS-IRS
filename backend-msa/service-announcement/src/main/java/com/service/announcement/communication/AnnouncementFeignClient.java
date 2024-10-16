package com.service.announcement.communication;

import com.service.common.dto.feign.ReadMemberRes;
import com.service.common.dto.feign.ReadRecruiterRes;
import com.service.common.dto.feign.ReadVerificationRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "announcementFeignClient", url = "http://localhost:8080")
public interface AnnouncementFeignClient {
    // TODO: 컨트롤러 보안 설정 auth 서비스가 실행되는 IP에 한해서만 내부 접근을 허용해야 한다.
    @GetMapping("/api/verification/read")
    ReadVerificationRes readVerification(@RequestParam("email") String email, @RequestParam Boolean flag);

    @GetMapping("/api/member/read")
    ReadRecruiterRes read(@RequestHeader("X-User-Email") String memberEmail, @RequestHeader("X-User-Role") String memberRole);
}
