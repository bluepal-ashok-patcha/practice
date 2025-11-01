package com.example.dto;

import com.example.entities.UserStatus;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private UserStatus status;
}
