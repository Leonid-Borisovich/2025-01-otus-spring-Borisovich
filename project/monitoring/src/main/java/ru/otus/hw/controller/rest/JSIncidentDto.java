package ru.otus.hw.controller.rest;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JSIncidentDto {

    private Long id;

    private String description;

    private String deviceId;

    private Long actionId;

    private String actionText;

    private Long actionTypeId;

    public JSIncidentDto(Long id, String description, String deviceId) {
        this.id = id;
        this.description = description;
        this.deviceId = deviceId;
    }
}
