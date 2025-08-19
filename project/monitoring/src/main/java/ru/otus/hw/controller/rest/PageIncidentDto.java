package ru.otus.hw.controller.rest;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageIncidentDto {

    private Long id;

    private String description;

    private String deviceId;

    private Long actionId;

    private String actionText;

    private Long actionTypeId;

    public PageIncidentDto(Long id, String description, String deviceId) {
        this.id = id;
        this.description = description;
        this.deviceId = deviceId;
    }
}
