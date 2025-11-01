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
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatMessageRepository messageRepository;

    @Autowired
    private KafkaTemplate<String, ChatMessage> kafkaTemplate;

    @Autowired
    private ContactService contactService;

    @Override
    public Chat createChat(ChatDto chatDto) {
        if (chatDto.getParticipants().size() == 2) {
            Long userId1 = (Long) chatDto.getParticipants().toArray()[0];
            Long userId2 = (Long) chatDto.getParticipants().toArray()[1];
            if (!contactService.isContact(userId1, userId2) || !contactService.isContact(userId2, userId1)) {
                throw new RuntimeException("Users are not contacts");
            }
        }

        Chat chat = new Chat();
        chat.setName(chatDto.getName());
        chat.setParticipants(chatDto.getParticipants());
        chat.setGroup(chatDto.getParticipants().size() > 2);
        chat.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return chatRepository.save(chat);
    }

    @Override
    public List<Chat> getUserChats(Long userId) {
        return chatRepository.findByParticipantsContaining(userId);
    }

    @Override
    public List<ChatMessage> getChatMessages(Long chatId) {
        return messageRepository.findByChatId(chatId);
    }

    @Override
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
