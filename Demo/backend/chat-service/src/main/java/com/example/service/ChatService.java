package com.example.service;

import com.example.dto.ChatDto;
import com.example.dto.MessageDto;
import com.example.entities.Chat;
import com.example.entities.ChatMessage;
import com.example.repository.ChatRepository;
import com.example.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatMessageRepository messageRepository;

    @Autowired
    private KafkaTemplate<String, ChatMessage> kafkaTemplate;

    public Chat createChat(ChatDto chatDto) {
        Chat chat = new Chat();
        chat.setName(chatDto.getName());
        chat.setParticipants(chatDto.getParticipants());
        chat.setGroup(chatDto.getParticipants().size() > 2);
        chat.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return chatRepository.save(chat);
    }

    public List<Chat> getUserChats(Long userId) {
        return chatRepository.findByParticipantsContaining(userId);
    }

    public List<ChatMessage> getChatMessages(Long chatId) {
        return messageRepository.findByChatId(chatId);
    }

    public void sendMessage(Long userId, MessageDto messageDto) {
        ChatMessage message = new ChatMessage();
        message.setChatId(messageDto.getChatId());
        message.setSenderId(userId);
        message.setContent(messageDto.getContent());
        message.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        messageRepository.save(message);

        kafkaTemplate.send("chat", message);
    }
}
