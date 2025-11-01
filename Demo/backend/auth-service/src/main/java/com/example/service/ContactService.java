package com.example.service;

import com.example.dto.ContactDto;
import com.example.dto.UserDto;

import java.util.List;

public interface ContactService {
    void addContact(Long userId, ContactDto contactDto);
    List<UserDto> getContacts(Long userId);
}
