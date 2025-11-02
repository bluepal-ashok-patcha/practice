package com.example.controller;

import com.example.dto.ContactDto;
import com.example.dto.UserDto;
import com.example.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping
    public ResponseEntity<Void> addContact(@AuthenticationPrincipal Jwt jwt, @RequestBody ContactDto contactDto) {
        Long userId = jwt.getClaim("userId");
        contactService.addContact(userId, contactDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getContacts(@AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("userId");
        return ResponseEntity.ok(contactService.getContacts(userId));
    }
}
