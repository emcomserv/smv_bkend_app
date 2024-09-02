package com.smartbus.controller;

import com.smartbus.exception.BusInternalServerErrorException;
import com.smartbus.handler.AuthHandler;
import com.smartbus.model.request.LoginRequest;
import com.smartbus.model.request.SignupRequest;
import com.smartbus.model.response.LoginResponse;
import com.smartbus.model.response.SignupResponse;
import com.smartbus.security.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthHandler authHandler;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        return ResponseEntity.ok(authHandler.registerUser(signupRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            return ResponseEntity.ok(authHandler.generateToken(loginRequest));
        } catch (AuthenticationException e) {
            throw new BusInternalServerErrorException("Bad cred");
        }
    }
}
