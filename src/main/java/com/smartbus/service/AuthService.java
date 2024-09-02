package com.smartbus.service;

import com.smartbus.domain.User;
import com.smartbus.exception.BusBadRequestException;
import com.smartbus.model.request.SignupRequest;
import com.smartbus.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public User registerUser(SignupRequest signUpRequest) {
        if (userRepository.findByUsername(signUpRequest.getUsername()).isPresent()) {
            throw new BusBadRequestException("User already exists with same username");
        }
        return userRepository.save(buildUser(signUpRequest));
    }

    private User buildUser(SignupRequest signupRequest) {
        return User.builder()
                .username(signupRequest.getUsername())
                .phone(signupRequest.getPhone())
                .password(encodePassword("sachin"))
                .build();
    }

    private String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
