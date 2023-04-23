package com.example.hoeatserver.infrastructure.phone.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PhoneRequest {
    private String phoneNumber;
}
