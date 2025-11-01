package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.entities.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @Autowired
    private SimpMessagingTemplate template;

    @KafkaListener(topics = "chat", groupId = "chat")
    public void listen(ChatMessage message) {
        this.template.convertAndSend("/topic/chat/" + message.getChatId(), message);
    }
}
