package com.example.hoeatserver.global.security.jwt;

import com.example.hoeatserver.global.security.principle.AuthDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtParser {
    private final AuthDetailsService authDetailsService;
    @Value("${spring.jwt.key}")
    private String key;

    private final String JWT_HEADER = "Authorization";
    private final String JWT_PREFIX = "Bearer";

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = authDetailsService.loadUserByUsername(getTokenSubject(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JWT_HEADER);
        return parseToken(bearerToken);
    }

    public String parseToken(String token) {
        if (token != null && token.startsWith(JWT_PREFIX)){
            return token.replace(JWT_PREFIX, "");
        }else return null;
    }

    private String getTokenSubject(String subject){
        return parseTokenBody(subject).getSubject();
    }

    private Claims parseTokenBody(String token){
        try {
            return Jwts.parser().setSigningKey(key)
                    .parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }
}
