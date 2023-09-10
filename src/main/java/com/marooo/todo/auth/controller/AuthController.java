package com.marooo.todo.auth.controller;

import com.marooo.todo.auth.dto.Jwt;
import com.marooo.todo.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @RequestMapping("/login")
    public ResponseEntity<Jwt> login(@RequestParam String username) {
        return ResponseEntity.ok(authService.login(username));
    }
}
