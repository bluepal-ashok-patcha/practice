package com.example.service;

import com.example.dto.LoginDto;
import com.example.dto.RegisterDto;
import com.example.entities.User;

public interface AuthenticationService {
    String login(LoginDto loginDto);
    User register(RegisterDto registerDto);
}
