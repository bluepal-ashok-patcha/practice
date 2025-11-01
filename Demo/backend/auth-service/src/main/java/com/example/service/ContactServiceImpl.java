package com.example.service;

import com.example.dto.ContactDto;
import com.example.dto.UserDto;
import com.example.entities.Contact;
import com.example.entities.User;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.ContactRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addContact(Long userId, ContactDto contactDto) {
        User contactUser = userRepository.findByUsername(contactDto.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + contactDto.getUsername()));

        Contact contact = new Contact();
        contact.setUserId(userId);
        contact.setContactId(contactUser.getId());
        contactRepository.save(contact);
    }

    @Override
    public List<UserDto> getContacts(Long userId) {
        List<Contact> contacts = contactRepository.findByUserId(userId);
        List<Long> contactIds = contacts.stream().map(Contact::getContactId).collect(Collectors.toList());
        return userRepository.findAllById(contactIds).stream().map(this::toDto).collect(Collectors.toList());
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
