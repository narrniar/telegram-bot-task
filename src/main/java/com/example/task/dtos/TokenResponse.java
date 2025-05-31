package com.example.task.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class TokenResponse {

    private String token;
    private String message;
}
