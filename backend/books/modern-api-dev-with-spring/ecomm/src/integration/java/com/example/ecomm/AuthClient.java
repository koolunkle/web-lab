package com.example.ecomm;

import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import com.example.ecomm.model.SignInReq;
import com.example.ecomm.model.SignedInUser;

import lombok.RequiredArgsConstructor;

import tools.jackson.databind.ObjectMapper;

@RequiredArgsConstructor
public class AuthClient {

    private final TestRestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    public SignedInUser login(String username, String password) {
        SignInReq signInReq = new SignInReq().username(username).password(password);

        return restTemplate
                .execute("/api/v1/auth/token", HttpMethod.POST, request -> {
                    objectMapper.writeValue(request.getBody(), signInReq);
                    request.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    request.getHeaders().add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
                },
                        response -> objectMapper.readValue(response.getBody(), SignedInUser.class));
    }
}
