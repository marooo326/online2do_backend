package com.marooo.todo.controller.dto;

import lombok.*;

public class TodoResponseDto {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class TodoDto {
        private Long id;
        private String contents;
        private Boolean isCompleted;
        private String author;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class TodoStateDto {
        private Long id;
        private Boolean isCompleted;
    }
}
