package com.example.hoeatserver.domain.user.presentation.dto.response;


import lombok.Builder;
import lombok.Getter;

import javax.management.relation.Role;

@Getter
@Builder
public class UserResponse {
    private final String email;

    private final Role role;
}
