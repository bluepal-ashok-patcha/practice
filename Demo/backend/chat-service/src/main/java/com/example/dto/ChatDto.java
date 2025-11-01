package com.example.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.Set;

@Data
public class ChatDto {
    private String name;

    @NotEmpty(message = "Participants are required")
    private Set<Long> participants;
}
