package com.marooo.todo.controller;

import com.marooo.todo.auth.annotation.AuthUser;
import com.marooo.todo.controller.dto.TodoRequestDto;
import com.marooo.todo.controller.dto.TodoResponseDto;
import com.marooo.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping("")
    public ResponseEntity<List<TodoResponseDto.TodoDto>> getTodoList() {
        return new ResponseEntity<>(todoService.findAllTodos(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Long> createTodo(@RequestParam String contents, @AuthUser String username) {
        return new ResponseEntity<>(todoService.saveTodo(username, contents), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<TodoResponseDto.TodoStateDto> updateTodo(@RequestBody TodoRequestDto.UpdateStateDto updateStateDto) {
        return new ResponseEntity<>(todoService.updateTodo(updateStateDto.getId(), updateStateDto.getState()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("")
    public ResponseEntity<Long> deleteTodo(@RequestParam Long id) {
        return new ResponseEntity<>(todoService.deleteTodo(id), HttpStatus.OK);
    }
}
