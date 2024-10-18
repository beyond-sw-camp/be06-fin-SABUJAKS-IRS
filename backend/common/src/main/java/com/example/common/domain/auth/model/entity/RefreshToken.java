package com.example.common.domain.auth.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 24 * 5) // TTL 1시간 = 3600, 5일
@AllArgsConstructor
@Getter
@ToString
public class RefreshToken {

    @Id
    private String email;  // 이메일을 키로 사용
    private String refreshToken; // 리프레시 토큰
}
