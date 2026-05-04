package com.eazybytes.eazystore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.eazystore.dto.ProfileResponseDto;
import com.eazybytes.eazystore.service.IprofileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final IprofileService iprofileService;

    @GetMapping
    public ResponseEntity<ProfileResponseDto> getProfile() {
        ProfileResponseDto profileResponseDto = iprofileService.getProfile();
        
        return ResponseEntity.ok(profileResponseDto);
    }
}
