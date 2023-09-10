package com.marooo.todo.service;

import com.marooo.todo.controller.dto.TodoResponseDto;
import com.marooo.todo.converter.TodoConverter;
import com.marooo.todo.domain.Todo;
import com.marooo.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    @Transactional
    public List<TodoResponseDto.TodoDto> findAllTodos() {
        return todoRepository.findAll().stream().map(TodoConverter::toTodoDto).toList();
    }

    @Transactional
    public Long saveTodo(String username, String contents) {
        Todo newTodo = Todo.builder()
                .contents(contents)
                .author(username)
                .isCompleted(false)
                .build();
        return todoRepository.save(newTodo).getId();
    }

    @Transactional
    public TodoResponseDto.TodoStateDto updateTodo(Long id, Boolean state) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Todo with id" + id + " not Found")); //예외처리 추후적용
        todo.updateTodo(state);
        return TodoConverter.toTodoStateDto(todoRepository.save(todo));
    }

    @Transactional
    public Long deleteTodo(Long id) {
        todoRepository.deleteById(id);
        return id;
    }

}
