package com.example.service;

import com.example.dto.ProfilePhotoDto;
import com.example.dto.UserDto;
import com.example.entities.User;
import com.example.entities.UserStatus;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDto updateUserStatus(Long userId, UserStatus status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setStatus(status);
        userRepository.save(user);
        return toDto(user);
    }

    public UserDto updateProfilePhoto(Long userId, ProfilePhotoDto profilePhotoDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setProfilePhoto(profilePhotoDto.getProfilePhoto());
        userRepository.save(user);
        return toDto(user);
    }

    public List<UserDto> getOnlineUsers() {
        return userRepository.findAll().stream()
                .filter(user -> user.getStatus() == UserStatus.ONLINE)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setStatus(user.getStatus());
        return dto;
    }
}
