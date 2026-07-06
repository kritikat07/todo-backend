package com.todobackend.service;

import com.todobackend.dto.TodoRequest;
import com.todobackend.dto.TodoResponse;
import com.todobackend.exception.ResourceNotFoundException;
import com.todobackend.model.Todo;
import com.todobackend.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoResponse createTodo(TodoRequest request, String owner) {
        Todo todo = new Todo();
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setCompleted(request.isCompleted());
        todo.setOwnerUsername(owner);

        Todo saved = todoRepository.save(todo);
        return toResponse(saved);
    }

    public List<TodoResponse> getAllTodos(String owner) {
        return todoRepository.findByOwnerUsername(owner)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public TodoResponse getTodoById(Long id, String owner) {
        Todo todo = findOwnedTodo(id, owner);
        return toResponse(todo);
    }

    public TodoResponse updateTodo(Long id, TodoRequest request, String owner) {
        Todo todo = findOwnedTodo(id, owner);
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setCompleted(request.isCompleted());

        Todo updated = todoRepository.save(todo);
        return toResponse(updated);
    }

    public TodoResponse markAsCompleted(Long id, String owner) {
        Todo todo = findOwnedTodo(id, owner);
        todo.setCompleted(true);
        Todo updated = todoRepository.save(todo);
        return toResponse(updated);
    }

    public void deleteTodo(Long id, String owner) {
        Todo todo = findOwnedTodo(id, owner);
        todoRepository.delete(todo);
    }

    private Todo findOwnedTodo(Long id, String owner) {
        Todo todo = todoRepository.findByIdAndOwnerUsername(id, owner);
        if (todo == null) {
            throw new ResourceNotFoundException("Todo not found with id: " + id);
        }
        return todo;
    }

    private TodoResponse toResponse(Todo todo) {
        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.isCompleted(),
                todo.getCreatedAt(),
                todo.getUpdatedAt()
        );
    }
}
