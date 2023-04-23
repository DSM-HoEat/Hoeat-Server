package com.example.hoeatserver.global.security.jwt;

import com.example.hoeatserver.domain.user.domain.User;
import com.example.hoeatserver.global.security.jwt.dto.TokenResponse;
import com.example.hoeatserver.global.security.jwt.entity.RefreshToken;
import com.example.hoeatserver.global.security.jwt.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    private final RefreshTokenRepository refreshTokenRepository;

    private final String ACCESS_KEY = "access";
    private final String REFRESH_KEY = "refresh";

    @Value("${spring.jwt.key}")
    private String key;

    @Value("${spring.jwt.live.atk}")
    private Long atkTime;

    @Value("${spring.jwt.live.rtk}")
    private Long rtkTime;

    @PostConstruct
    protected void init() {
        key = Base64.getEncoder().encodeToString(key.getBytes());
    }

    public TokenResponse getToken(User user) {
        String atk = generateAccessToken(user);
        String rtk = generateRefreshToken(user);

        return new TokenResponse(atk, rtk, atkTime);
    }

    public String generateAccessToken(User user) {
        return createToken(user, atkTime, ACCESS_KEY);
    }

    public String generateRefreshToken(User user) {
        String rtk = createToken(user, rtkTime, REFRESH_KEY);
        refreshTokenRepository.save(
                new RefreshToken(
                        user.getEmail(),
                        rtk,
                        rtkTime
                )
        );
        return rtk;
    }

    public String createToken(User user, Long tokenTime, String type) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("roles", user.getRole());
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, key)
                .setClaims(claims)
                .setHeaderParam("type", type)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tokenTime))
                .compact();
    }

}
