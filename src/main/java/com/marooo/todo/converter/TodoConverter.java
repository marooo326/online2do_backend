package com.marooo.todo.converter;

import com.marooo.todo.controller.dto.TodoResponseDto;
import com.marooo.todo.domain.Todo;

public class TodoConverter {
    public static TodoResponseDto.TodoDto toTodoDto(Todo todo) {
        return TodoResponseDto.TodoDto.builder()
                .id(todo.getId())
                .contents(todo.getContents())
                .isCompleted(todo.getIsCompleted())
                .author(todo.getAuthor())
                .build();
    }

    public static TodoResponseDto.TodoStateDto toTodoStateDto(Todo todo) {
        return TodoResponseDto.TodoStateDto.builder()
                .id(todo.getId())
                .isCompleted(todo.getIsCompleted())
                .build();
    }
}
