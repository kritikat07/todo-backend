package com.todobackend.controller;

import com.todobackend.dto.TodoRequest;
import com.todobackend.dto.TodoResponse;
import com.todobackend.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    // POST /api/todos -> create a todo
    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(@Valid @RequestBody TodoRequest request,
                                                    Authentication authentication) {
        String owner = authentication.getName();
        return ResponseEntity.ok(todoService.createTodo(request, owner));
    }

    // GET /api/todos -> list all todos of logged-in user
    @GetMapping
    public ResponseEntity<List<TodoResponse>> getAllTodos(Authentication authentication) {
        String owner = authentication.getName();
        return ResponseEntity.ok(todoService.getAllTodos(owner));
    }

    // GET /api/todos/{id} -> get single todo
    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> getTodoById(@PathVariable Long id, Authentication authentication) {
        String owner = authentication.getName();
        return ResponseEntity.ok(todoService.getTodoById(id, owner));
    }

    // PUT /api/todos/{id} -> update a todo
    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodo(@PathVariable Long id,
                                                    @Valid @RequestBody TodoRequest request,
                                                    Authentication authentication) {
        String owner = authentication.getName();
        return ResponseEntity.ok(todoService.updateTodo(id, request, owner));
    }

    // PATCH /api/todos/{id}/complete -> mark as read/completed
    @PatchMapping("/{id}/complete")
    public ResponseEntity<TodoResponse> markAsCompleted(@PathVariable Long id, Authentication authentication) {
        String owner = authentication.getName();
        return ResponseEntity.ok(todoService.markAsCompleted(id, owner));
    }

    // DELETE /api/todos/{id} -> delete a todo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id, Authentication authentication) {
        String owner = authentication.getName();
        todoService.deleteTodo(id, owner);
        return ResponseEntity.noContent().build();
    }
}
