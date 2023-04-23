package com.example.hoeatserver.infrastructure.phone.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PhoneCodeRequest {

    private String phoneNumber;
    private String code;
}
