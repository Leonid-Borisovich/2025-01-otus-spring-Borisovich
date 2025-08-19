package ru.otus.hw.dto;

import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class IncidentDto {
    private long id;

    private String description;

    @EqualsAndHashCode.Exclude
    private DeviceDto deviceDto;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<ActionDto> actionDtos;

}
