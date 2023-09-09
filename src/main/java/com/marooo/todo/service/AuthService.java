package com.marooo.todo.service;

import com.marooo.todo.auth.dto.Jwt;
import com.marooo.todo.auth.provider.JwtProvider;
import com.marooo.todo.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtProvider jwtProvider;

    public Jwt login(String username) {
        // 임시 인증이므로 인증과정 생략
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + Role.USER);
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, Collections.singletonList(authority));
        return jwtProvider.createToken(authentication);
    }
}
