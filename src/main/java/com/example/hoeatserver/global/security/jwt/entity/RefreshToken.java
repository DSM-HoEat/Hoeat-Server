package com.example.hoeatserver.global.security.jwt.entity;

import org.springframework.data.annotation.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@AllArgsConstructor
@RedisHash("RefreshToken")
public class RefreshToken {

    @Id
    private String email;

    @Indexed
    @NotBlank
    private String refreshToken;

    @TimeToLive
    private Long rtkTime;

}
