package com.eazybytes.eazystore.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.eazystore.dto.ContactRequestDto;
import com.eazybytes.eazystore.service.IContactService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final IContactService contactService;

    @PostMapping
    public ResponseEntity<String> saveContact(@RequestBody ContactRequestDto contactRequestDto) {
        boolean isSaved = contactService.saveContact(contactRequestDto);
        if (isSaved) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Request processed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred. Please try again or contact Dev team");
        }
    }
}
