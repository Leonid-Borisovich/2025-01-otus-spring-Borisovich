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

    private Long deviceId;

    private String rawActionsText;

    public JSIncidentDto(Long id, String description, Long deviceId) {
        this.id = id;
        this.description = description;
        this.deviceId = deviceId;
    }
}
