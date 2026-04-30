package com.eazybytes.eazystore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.eazystore.scopes.RequestScopedBean;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/scope")
@RequiredArgsConstructor
public class ScopeController {

    private final RequestScopedBean requestScopedBean;

    @GetMapping("/request")
    public ResponseEntity<String> testRequestScope() {
        requestScopedBean.setUserName("Jane Doe");
        return ResponseEntity.ok(requestScopedBean.getUserName());
    }

    @GetMapping("/test")
    public ResponseEntity<String> testScope() {
        return ResponseEntity.ok(requestScopedBean.getUserName());
    }
}
