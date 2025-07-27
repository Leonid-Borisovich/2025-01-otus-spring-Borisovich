package ru.otus.hw.controller.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.dto.ActionDto;
import ru.otus.hw.dto.IncidentDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Action;
import ru.otus.hw.services.IncidentService;
import ru.otus.hw.services.ActionService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class IncidentsController {

    private final IncidentService incidentService;
    private final ActionService actionService;

    @GetMapping("/admin")
    public String publicPage() {
        return "with role ADMIN";
    }

    @GetMapping("/auth")
    public String authPage() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return "I am " + userDetails.getUsername();
    }


    @PreAuthorize(" hasAnyRole('OPERATOR', 'ADMIN') ")
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

    @PreAuthorize("hasRole('OPERATOR') ")
    @PatchMapping("/api/v1/incident/{id}")
    public IncidentDto addAction(
                             @PathVariable(value = "id", required = true) long id,
                             @Valid
                             @RequestBody
                             JSActionDto jsActionDtoDto
    ) {

        IncidentDto incidentDto =  incidentService.findById(id).orElse(null);
        if (incidentDto == null)
            return null;
        ActionDto actionDto = actionService.insert(jsActionDtoDto.getActionText(), id, jsActionDtoDto.getActionTypeId());
        incidentDto.getActionDtos().add(actionDto);
        //incidentService.
        return incidentDto;

    }

    @PostMapping("/api/v1/incident/")
    public IncidentDto addNewIncident(
            @Valid
            @RequestBody
            JSIncidentDto jsIncidentDto) {
        IncidentDto newIncident = incidentService.insert(jsIncidentDto.getDescription(), jsIncidentDto.getDeviceId());
        Long newIncidentId = newIncident.getId();
        return incidentService.findById(newIncidentId).orElse(null);
    }

}
