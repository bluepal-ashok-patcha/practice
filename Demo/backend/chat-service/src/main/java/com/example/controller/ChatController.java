package com.example.controller;

import com.example.dto.ChatDto;
import com.example.dto.MessageDto;
import com.example.entities.Chat;
import com.example.entities.ChatMessage;
import com.example.service.ChatService;
import com.example.dto.ReadReceiptDto;
import com.example.dto.TypingIndicatorDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/read")
    public void handleReadReceipt(@Payload ReadReceiptDto readReceiptDto) {
        messagingTemplate.convertAndSend("/topic/read/" + readReceiptDto.getMessageId(), readReceiptDto);
    }

    @MessageMapping("/typing")
    public void handleTypingIndicator(@Payload TypingIndicatorDto typingIndicatorDto) {
        messagingTemplate.convertAndSend("/topic/typing/" + typingIndicatorDto.getChatId(), typingIndicatorDto);
    }

    @PostMapping("/chats")
    public ResponseEntity<Chat> createChat(@Valid @RequestBody ChatDto chatDto) {
        return ResponseEntity.ok(chatService.createChat(chatDto));
    }

    @GetMapping("/chats")
    public ResponseEntity<List<Chat>> getUserChats(@AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("userId");
        return ResponseEntity.ok(chatService.getUserChats(userId));
    }

    @GetMapping("/chats/{chatId}/messages")
    public ResponseEntity<List<ChatMessage>> getChatMessages(@PathVariable Long chatId) {
        return ResponseEntity.ok(chatService.getChatMessages(chatId));
    }

    @PostMapping("/messages")
    public ResponseEntity<Void> sendMessage(@AuthenticationPrincipal Jwt jwt, @Valid @RequestBody MessageDto messageDto) {
        Long userId = jwt.getClaim("userId");
        chatService.sendMessage(userId, messageDto);
        return ResponseEntity.ok().build();
    }
}
