package com.example.task.models;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@EqualsAndHashCode(callSuper = true) // Crucial for inherited fields from 'Token' to be included in equals/hashCode checks
@Entity // This class IS an entity, and will have its own 'jwt_tokens' table
@Table(name = "jwt_tokens") // Explicitly names the table
@Getter // Generates getters for all fields
@Setter // Generates setters for all fields
@NoArgsConstructor // Generates a no-argument constructor (required by JPA)
@AllArgsConstructor
public class JwtToken extends Token {
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public JwtToken(String tokenValue, User user) {
        super(); // Calls the no-argument constructor of the Token superclass
        this.setToken(tokenValue); // Set the tokenValue inherited from Token
        this.user = user;
        // createdAt is handled by @CreationTimestamp in Token
    }



}
