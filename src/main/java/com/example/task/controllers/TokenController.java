package com.example.task.controllers;

import com.example.task.dtos.TokenResponse;
import com.example.task.services.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/token")
@Tag(name = "Token", description = "Token management API")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/generate")
    @Operation(summary = "Generate Telegram bot token")
    public ResponseEntity<TokenResponse> generateToken() {
        try {
            TokenResponse response = tokenService.generateToken();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
