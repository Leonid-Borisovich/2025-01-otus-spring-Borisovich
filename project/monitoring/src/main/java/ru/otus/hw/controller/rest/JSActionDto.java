package ru.otus.hw.controller.rest;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JSActionDto {

    private String actionText;

    private Long actionTypeId;

}
