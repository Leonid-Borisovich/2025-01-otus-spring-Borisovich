package ru.otus.hw.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

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
