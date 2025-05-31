package com.example.task.controllers;

import com.example.task.dtos.MessageRequest;
import com.example.task.dtos.MessageResponse;
import com.example.task.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@Tag(name = "Messages", description = "Message management API")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/send")
    @Operation(summary = "Send message to Telegram bot")
    public ResponseEntity<MessageResponse> sendMessage(@Valid @RequestBody MessageRequest messageRequest) {
        try {
            MessageResponse response = messageService.sendMessage(messageRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    @Operation(summary = "Get all user messages")
    public ResponseEntity<List<MessageResponse>> getAllMessages() {
        try {
            List<MessageResponse> messages = messageService.getAllMessages();
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
