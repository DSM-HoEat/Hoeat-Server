package com.example.hoeatserver.domain.user.facade;

import com.example.hoeatserver.domain.user.domain.User;
import com.example.hoeatserver.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserFacade {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserByEmail(email);
    }

    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ErrorCode.USER_NOT_FOUND"));

    }
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("ErrorCode.EMAIL_NOT_FOUND"));
    }

    public void existsByEmail(String email){
        if(userRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Exists User");
        }
    }
}
