package com.example.task.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String username;
    private String name;


}