package com.example.service;

import com.example.entities.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "chat", groupId = "chat")
    public void consume(ChatMessage chatMessage) {
        logger.info("Consumed message: {}", chatMessage);
        messagingTemplate.convertAndSend("/topic/chat/" + chatMessage.getChatId(), chatMessage);
    }
}
