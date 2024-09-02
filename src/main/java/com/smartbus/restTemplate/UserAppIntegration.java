package com.smartbus.restTemplate;

import com.smartbus.model.request.SignupRequest;
import com.smartbus.model.response.SignupResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAppIntegration {

    private final RestTemplate restTemplate;

    public SignupResponse callUserServiceIntegration(SignupRequest signupRequest) {
        String url = "";
        HttpEntity<SignupRequest> httpEntity = new HttpEntity<SignupRequest>(signupRequest, getHttpHeaders());
        ResponseEntity<SignupResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, SignupResponse.class);
        return responseEntity.getBody();
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
