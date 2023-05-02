package com.example.hoeatserver.domain.user.service;

import com.example.hoeatserver.domain.user.domain.User;
import com.example.hoeatserver.domain.user.facade.UserFacade;
import com.example.hoeatserver.domain.user.presentation.dto.request.LoginRequest;
import com.example.hoeatserver.global.security.jwt.JwtParser;
import com.example.hoeatserver.global.security.jwt.JwtProvider;
import com.example.hoeatserver.global.security.jwt.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginUserService {

    private final UserFacade userFacade;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public TokenResponse execute(LoginRequest request){
        User user = userFacade.getUserByEmail(request.getEmail());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("Password Miss Matched");
        }

        return jwtProvider.getToken(user);
    }
}
