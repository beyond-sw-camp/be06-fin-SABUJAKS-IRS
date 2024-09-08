package com.sabujaks.irs.domain.test;

import com.sabujaks.irs.domain.auth.model.response.AuthSignupRes;
import com.sabujaks.irs.global.common.responses.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/ex01")
    public ResponseEntity<String> ex01(){
        return ResponseEntity.ok("ex01");
    }
}
