package com.example.controller;

import com.example.dto.ProfilePhotoDto;
import com.example.dto.UserDto;
import com.example.entities.UserStatus;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/{userId}/status")
    public ResponseEntity<UserDto> updateUserStatus(@PathVariable Long userId, @RequestBody UserStatus status) {
        return ResponseEntity.ok(userService.updateUserStatus(userId, status));
    }

    @PutMapping("/{userId}/profile-photo")
    public ResponseEntity<UserDto> updateProfilePhoto(@PathVariable Long userId, @RequestBody ProfilePhotoDto profilePhotoDto) {
        return ResponseEntity.ok(userService.updateProfilePhoto(userId, profilePhotoDto));
    }

    @GetMapping("/online")
    public ResponseEntity<List<UserDto>> getOnlineUsers() {
        return ResponseEntity.ok(userService.getOnlineUsers());
    }
}
