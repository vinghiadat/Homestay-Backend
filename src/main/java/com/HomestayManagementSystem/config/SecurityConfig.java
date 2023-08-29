package com.HomestayManagementSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // biết để quét qua cấu hình của Security
@EnableWebSecurity // Bật bảo mật web
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((a) -> a.disable())
        .authorizeHttpRequests(
            (request) -> request.anyRequest().permitAll()
        )
        .httpBasic((s) ->{});
        return http.build();
    }

    @Bean // Cấu hình bean cho mã hóa mật khẩu
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
