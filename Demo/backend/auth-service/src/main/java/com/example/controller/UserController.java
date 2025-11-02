package com.example.controller;

import com.example.dto.ProfilePhotoDto;
import com.example.dto.UserDto;
import com.example.entities.UserStatus;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/status")
    public ResponseEntity<UserDto> updateUserStatus(@AuthenticationPrincipal Jwt jwt, @RequestBody UserStatus status) {
        Long userId = jwt.getClaim("userId");
        return ResponseEntity.ok(userService.updateUserStatus(userId, status));
    }

    @PutMapping("/profile-photo")
    public ResponseEntity<UserDto> updateProfilePhoto(@AuthenticationPrincipal Jwt jwt, @RequestBody ProfilePhotoDto profilePhotoDto) {
        Long userId = jwt.getClaim("userId");
        return ResponseEntity.ok(userService.updateProfilePhoto(userId, profilePhotoDto));
    }

    @GetMapping("/online")
    public ResponseEntity<List<UserDto>> getOnlineUsers() {
        return ResponseEntity.ok(userService.getOnlineUsers());
    }
}
