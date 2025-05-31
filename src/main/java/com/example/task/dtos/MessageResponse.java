package com.example.task.dtos;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ReflectiveScan;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class MessageResponse {
    private Long id;
    private String content;
    private LocalDateTime sentAt;
}
