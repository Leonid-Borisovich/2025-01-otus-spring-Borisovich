package ru.otus.hw.controller.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.dto.ActionDto;
import ru.otus.hw.dto.IncidentDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
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

    @PreAuthorize(" hasAnyRole('OPERATOR', 'ADMIN') ")
    @GetMapping("/api/v1/incident/{id}")
    public IncidentDto getIncident(@PathVariable(value = "id", required = true) long id) {
        return  incidentService.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @PreAuthorize(" hasAnyRole('OPERATOR', 'ADMIN') ")
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
                             PageActionDto pageActionDtoDto
    ) {

        IncidentDto incidentDto =  incidentService.findById(id).orElse(null);
        if (incidentDto == null)
            return null;
        ActionDto actionDto = actionService.insert(pageActionDtoDto.getActionText(), id, pageActionDtoDto.getActionTypeId());
        incidentDto.getActionDtos().add(actionDto);
        return incidentDto;

    }

    @PreAuthorize(" hasAnyRole('OPERATOR', 'ADMIN') ")
    @PostMapping("/api/v1/incident/")
    public IncidentDto addNewIncident(
            @Valid
            @RequestBody
            PageIncidentDto pageIncidentDto) {
        IncidentDto newIncident = incidentService.insert(pageIncidentDto.getDescription(), pageIncidentDto.getDeviceId());
        Long newIncidentId = newIncident.getId();
        return incidentService.findById(newIncidentId).orElse(null);
    }

}
