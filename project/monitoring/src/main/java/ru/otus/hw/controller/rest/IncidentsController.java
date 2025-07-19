package ru.otus.hw.controller.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.dto.IncidentDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.IncidentService;
import ru.otus.hw.services.ActionService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class IncidentsController {

    private final IncidentService incidentService;
    private final ActionService actionService;


    @GetMapping("/api/v1/incident/")
    public List<IncidentDto> listPage() {
        List<IncidentDto> incidentDtos = incidentService.findAll();
        return incidentDtos;
    }

    @GetMapping("/api/v1/incident/{id}")
    public IncidentDto getIncident(@PathVariable(value = "id", required = true) long id) {
        return  incidentService.findById(id).orElseThrow(EntityNotFoundException::new);
    }


    @DeleteMapping("/api/v1/incident/{id}")
    public ResponseEntity deleteIncident(@PathVariable(value = "id", required = true) long id) {
        incidentService.findById(id).orElseThrow(EntityNotFoundException::new);
        incidentService.deleteById(id);
        return ResponseEntity.ok("Deleted is success");
    }

    @PutMapping("/api/v1/incident/{id}")
    public IncidentDto updatePerson(
                             @PathVariable(value = "id", required = true) long id,
                             @Valid
                             @RequestBody
                             JSIncidentDto jsIncidentDto
    ) {
        IncidentDto incidentUpdated = incidentService.update(id, jsIncidentDto.getDescription(), jsIncidentDto.getDeviceId());

        List<String> updatedActions = jsIncidentDto.getRawActionsText() == null ?
                new ArrayList() :
                new ArrayList<>(Arrays.asList(jsIncidentDto.getRawActionsText().split("\n")));
        actionService.setAll(id, updatedActions);
        return incidentService.findById(incidentUpdated.getId()).orElse(null);

    }

    @PostMapping("/api/v1/incident/")
    public IncidentDto addNewIncident(
            @Valid
            @RequestBody
            JSIncidentDto jsIncidentDto) {
        IncidentDto newIncident = incidentService.insert(jsIncidentDto.getDescription(), jsIncidentDto.getDeviceId());
        List<String> updatedActions = jsIncidentDto.getRawActionsText() == null ?
                new ArrayList() :
                new ArrayList<>(Arrays.asList(jsIncidentDto.getRawActionsText().split("\n")));
        Long newIncidentId = newIncident.getId();
        actionService.setAll(newIncidentId, updatedActions);
        return incidentService.findById(newIncidentId).orElse(null);
    }

}
