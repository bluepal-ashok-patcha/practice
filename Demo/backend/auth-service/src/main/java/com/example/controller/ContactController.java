package com.example.controller;

import com.example.dto.ContactDto;
import com.example.dto.UserDto;
import com.example.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping
    public ResponseEntity<Void> addContact(@PathVariable Long userId, @RequestBody ContactDto contactDto) {
        contactService.addContact(userId, contactDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getContacts(@PathVariable Long userId) {
        return ResponseEntity.ok(contactService.getContacts(userId));
    }
}
