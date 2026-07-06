package com.todobackend.repository;

import com.todobackend.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByOwnerUsername(String ownerUsername);
    Todo findByIdAndOwnerUsername(Long id, String ownerUsername);
}
