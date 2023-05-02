package com.example.hoeatserver.global.security.jwt.repository;

import com.example.hoeatserver.global.security.jwt.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    boolean existsByEmail(String email);
}
