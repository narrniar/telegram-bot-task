package com.example.task.repositories;

import com.example.task.models.Message;
import com.example.task.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByUserOrderBySentAtDesc(User user);
}
