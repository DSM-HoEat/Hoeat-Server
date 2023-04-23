package com.example.hoeatserver.global.security;


import com.example.hoeatserver.global.security.jwt.JwtAuthenticationFilter;
import com.example.hoeatserver.global.security.jwt.JwtParser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final JwtParser jwtParser;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{

        http.cors().configurationSource(request -> {
            var cors = new CorsConfiguration();
            cors.setAllowedOrigins(List.of("http://localhost:3000"));
            cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
            cors.setAllowedHeaders(List.of("*"));
            return cors;
        });

        http
                .formLogin().disable()
                .csrf().disable()
                .cors();

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .authorizeHttpRequests()
                .requestMatchers("/").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()

                .requestMatchers(HttpMethod.GET, "/oauth/google").permitAll()
                .requestMatchers(HttpMethod.GET, "/oauth/google/code").permitAll()
                .requestMatchers(HttpMethod.GET, "/oauth/kakao").permitAll()
                .requestMatchers(HttpMethod.GET, "/oauth/kakao/code").permitAll()
                .requestMatchers("/code").permitAll()

                .anyRequest().permitAll();

        http
                .addFilterBefore(new JwtAuthenticationFilter(jwtParser),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    protected BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
