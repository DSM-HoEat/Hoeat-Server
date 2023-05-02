package com.example.hoeatserver.domain.user.presentation;

import com.example.hoeatserver.domain.user.presentation.dto.request.LoginRequest;
import com.example.hoeatserver.domain.user.presentation.dto.request.SignUpRequest;
import com.example.hoeatserver.domain.user.service.LoginUserService;
import com.example.hoeatserver.domain.user.service.SignupUserService;
import com.example.hoeatserver.global.security.jwt.dto.TokenResponse;
import com.example.hoeatserver.infrastructure.phone.dto.request.PhoneRequest;
import com.example.hoeatserver.infrastructure.phone.service.PhoneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "User API 입니다.")
@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {
    private final PhoneService phoneService;

    private final SignupUserService signupUserService;

    private final LoginUserService loginUserService;

    @Operation(summary = "회원가입 코드 전송")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/code")
    public void Phone(@Valid @RequestBody PhoneRequest request) {
        phoneService.execute(request);
    }

    @Operation(summary = "회원가입")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public String signup(@Valid @RequestBody SignUpRequest request) {
        return signupUserService.execute(request);
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest request){
        return loginUserService.execute(request);
    }
}
