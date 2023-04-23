package com.example.hoeatserver.domain.user.service;

import com.example.hoeatserver.domain.user.domain.User;
import com.example.hoeatserver.domain.user.domain.repository.UserRepository;
import com.example.hoeatserver.domain.user.enums.ProviderType;
import com.example.hoeatserver.domain.user.facade.UserFacade;
import com.example.hoeatserver.domain.user.presentation.dto.request.SignUpRequest;
import com.example.hoeatserver.infrastructure.phone.entity.PhoneCode;
import com.example.hoeatserver.infrastructure.phone.repository.CodeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignupUserService {

    private final CodeRepository codeRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserFacade userFacade;

    @Transactional
    public String execute(SignUpRequest request) {

        userFacade.existsByEmail(request.getEmail());

        if (!request.getPassword().equals(request.getPasswordValid())) {
            throw new IllegalArgumentException("PassWord Miss Matched");
        }

        PhoneCode phoneCode = codeRepository.findPhoneCodeByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new IllegalArgumentException("Not_Found_Phone_Number"));

        if (!phoneCode.getValue().equals(request.getCode())) {
            throw new IllegalArgumentException("Code Miss Match");
        }

        userRepository.save(User.builder()
                .email(request.getEmail())
                .nickName(request.getNickName())
                .password(passwordEncoder.encode(request.getPassword()))
                .providerType(ProviderType.LOCAL)
                .build());

        return request.getNickName();
    }
}
