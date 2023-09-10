package com.marooo.todo.controller.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

public class TodoRequestDto {

    @Getter
    public static class UpdateStateDto {
        @NotBlank
        private Long id;

        @NotBlank
        private Boolean state;
    }

}
