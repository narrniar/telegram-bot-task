package com.example.task.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true) // Crucial for inherited fields from 'Token' to be included in equals/hashCode checks
@Entity // This class IS an entity, and will have its own 'telegram_tokens' table
@Table(name = "telegram_tokens") // Explicitly names the table
@Getter // Generates getters for all fields
@Setter // Generates setters for all fields
@NoArgsConstructor // Generates a no-argument constructor (required by JPA)
@AllArgsConstructor // Generates a constructor with all fields (including inherited ones)
public class TelegramToken extends Token { // Extends the @MappedSuperclass 'Token'

    @Column(name = "telegram_chat_id", unique = true)
    // Telegram chat ID is unique per token and mandatory
    private Long telegramChatId;

    @OneToOne(fetch = FetchType.LAZY) // Lazy fetching for performance; load User only when explicitly accessed
    @JoinColumn(name = "user_id", unique = true, nullable = false) // Foreign key column in 'telegram_tokens' table
    // 'unique = true' ensures a user can only have one TelegramToken linked in this specific one-to-one relationship.
    // 'nullable = false' ensures every TelegramToken is linked to a user.
    private User user; // This is the owning side of the OneToOne relationship

    // Custom constructor for creating a new TelegramToken (without ID, createdAt)
    // Useful for service layer when creating new instances.
    public TelegramToken(String tokenValue, Long telegramChatId, User user) {
        super(); // Call no-arg constructor of superclass (Token)
        this.setToken(tokenValue); // Set the tokenValue inherited from Token
        this.telegramChatId = telegramChatId;
        this.user = user;
        // createdAt is handled by @CreationTimestamp in Token
    }
}
