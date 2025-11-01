package com.example.service;

import com.example.dto.ProfilePhotoDto;
import com.example.dto.UserDto;
import com.example.entities.UserStatus;

import java.util.List;

public interface UserService {
    UserDto updateUserStatus(Long userId, UserStatus status);
    UserDto updateProfilePhoto(Long userId, ProfilePhotoDto profilePhotoDto);
    List<UserDto> getOnlineUsers();
}
