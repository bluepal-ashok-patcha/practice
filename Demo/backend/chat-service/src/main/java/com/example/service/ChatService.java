package com.example.service;

import com.example.dto.ChatDto;
import com.example.dto.MessageDto;
import com.example.entities.Chat;
import com.example.entities.ChatMessage;

import java.util.List;

public interface ChatService {
    Chat createChat(ChatDto chatDto);
    List<Chat> getUserChats(Long userId);
    List<ChatMessage> getChatMessages(Long chatId);
    void sendMessage(Long userId, MessageDto messageDto);
}
