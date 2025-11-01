package com.example.controller;

import com.example.dto.ChatDto;
import com.example.dto.MessageDto;
import com.example.entities.Chat;
import com.example.entities.ChatMessage;
import com.example.security.JwtUtil;
import com.example.model.User;
import com.example.service.ChatService;
import com.example.service.ReadReceiptService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.example.dto.ReadReceiptDto;
import com.example.dto.TypingIndicatorDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ReadReceiptService readReceiptService;

    @MessageMapping("/read")
    public void handleReadReceipt(@Payload ReadReceiptDto readReceiptDto) {
        readReceiptService.markMessageAsRead(readReceiptDto);
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
    public ResponseEntity<List<Chat>> getUserChats(@RequestHeader("Authorization") String token) {
        String username = jwtUtil.getUsernameFromJWT(token.substring(7));
        User user = userService.findByUsername(username);
        return ResponseEntity.ok(chatService.getUserChats(user.getId()));
    }

    @GetMapping("/chats/{chatId}/messages")
    public ResponseEntity<List<ChatMessage>> getChatMessages(@PathVariable Long chatId) {
        return ResponseEntity.ok(chatService.getChatMessages(chatId));
    }

    @PostMapping("/messages")
    public ResponseEntity<Void> sendMessage(@RequestHeader("Authorization") String token, @Valid @RequestBody MessageDto messageDto) {
        String username = jwtUtil.getUsernameFromJWT(token.substring(7));
        User user = userService.findByUsername(username);
        chatService.sendMessage(user.getId(), messageDto);
        return ResponseEntity.ok().build();
    }
}
