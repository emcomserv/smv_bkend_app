package com.smartbus.handler;

import com.smartbus.domain.User;
import com.smartbus.exception.BusInternalServerErrorException;
import com.smartbus.model.request.LoginRequest;
import com.smartbus.model.request.SignupRequest;
import com.smartbus.model.response.LoginResponse;
import com.smartbus.model.response.SignupResponse;
import com.smartbus.restTemplate.UserAppIntegration;
import com.smartbus.security.JwtUtils;
import com.smartbus.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthHandler {

    private final JwtUtils jwtUtils;
    private final AuthService authService;
    private final UserAppIntegration userAppIntegration;
    private final AuthenticationManager authenticationManager;

    public SignupResponse registerUser(SignupRequest signupRequest) {
        return Optional.ofNullable(authService.registerUser(signupRequest))
                .map(this::buildSignUpResponse)
                .orElseThrow(() -> new BusInternalServerErrorException("Something went wrong"));
    }

    public LoginResponse generateToken(LoginRequest loginRequest) {
        Authentication authentication = getAuthenticated(loginRequest);
        String token = jwtUtils.generateJwtToken(authentication);
        return new LoginResponse(token);
    }

    private Authentication getAuthenticated(LoginRequest loginRequest) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
    }

    private SignupResponse buildSignUpResponse(User user) {
        return SignupResponse.builder()
                .username(user.getUsername())
                .phoneNumber(user.getPhone())
                .password(user.getPassword())
                .build();
    }
}
