package com.example.task.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class MessageRequest {
    @NotBlank
    private String content;
}
