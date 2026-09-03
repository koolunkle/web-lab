package com.example.ecomm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecomm.UserApi;
import com.example.ecomm.entity.UserEntity;
import com.example.ecomm.exception.InvalidRefreshTokenException;
import com.example.ecomm.model.RefreshToken;
import com.example.ecomm.model.SignInReq;
import com.example.ecomm.model.SignedInUser;
import com.example.ecomm.model.User;
import com.example.ecomm.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthController implements UserApi {

    private final UserService service;

    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<SignedInUser> signIn(@Valid SignInReq signInReq) {
        UserEntity userEntity = service.findUserByUsername(signInReq.getUsername());

        if (passwordEncoder.matches(signInReq.getPassword(), userEntity.getPassword())) {
            return ResponseEntity.ok(service.getSignedInUser(userEntity));
        }

        throw new InsufficientAuthenticationException("Unauthorized.");
    }

    @Override
    public ResponseEntity<Void> signOut(@Valid RefreshToken refreshToken) {
        service.removeRefreshToken(refreshToken);
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<SignedInUser> signUp(@Valid User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(user).get());
    }

    @Override
    public ResponseEntity<SignedInUser> getAccessToken(@Valid RefreshToken refreshToken) throws Exception {
        return ResponseEntity
                .ok(service.getAccessToken(refreshToken)
                        .orElseThrow(() -> new InvalidRefreshTokenException("Invalid token.")));
    }
}
