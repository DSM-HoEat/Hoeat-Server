package com.example.hoeatserver.infrastructure.phone.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@AllArgsConstructor
@RedisHash(value = "PhoneCode")
public class PhoneCode {

    @Id
    @Indexed
    private String phoneNumber;

    @NotBlank
    private String value;

    @TimeToLive
    private Long phTime;
}
