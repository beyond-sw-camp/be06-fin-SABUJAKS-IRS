package com.sabujaks.irs.domain.system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/api/system-health")
    public ResponseEntity<String> systemHealth(){
        return ResponseEntity.ok("system-health!!");
    }
}
