package com.sabujaks.irs.domain.test;

import com.sabujaks.irs.domain.auth.model.response.AuthSignupRes;
import com.sabujaks.irs.global.common.responses.BaseResponse;
import com.sabujaks.irs.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/ex01")
    public ResponseEntity<String> ex01(){
        return ResponseEntity.ok("ex01제발");
    }

    @GetMapping("/ex02")
    public ResponseEntity<Collection<? extends GrantedAuthority>> ex02(@AuthenticationPrincipal CustomUserDetails userDetails){
        return ResponseEntity.ok(userDetails.getVideoInterviewAuthorities());
    }

    @GetMapping("/ex03")
    public ResponseEntity<String> ex03(@AuthenticationPrincipal CustomUserDetails userDetails){
        return ResponseEntity.ok("");
    }
}
