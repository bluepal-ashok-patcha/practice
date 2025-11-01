package com.example.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MessageDto {
    @NotNull(message = "Chat ID is required")
    private Long chatId;

    @NotEmpty(message = "Content is required")
    private String content;
}
