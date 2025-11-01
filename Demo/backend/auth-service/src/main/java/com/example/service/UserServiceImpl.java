package com.example.service;

import com.example.dto.ProfilePhotoDto;
import com.example.dto.UserDto;
import com.example.entities.User;
import com.example.entities.UserStatus;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto updateUserStatus(Long userId, UserStatus status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
        user.setStatus(status);
        userRepository.save(user);
        return toDto(user);
    }

    @Override
    public UserDto updateProfilePhoto(Long userId, ProfilePhotoDto profilePhotoDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + userId));
        user.setProfilePhoto(profilePhotoDto.getProfilePhoto());
        userRepository.save(user);
        return toDto(user);
    }

    @Override
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
