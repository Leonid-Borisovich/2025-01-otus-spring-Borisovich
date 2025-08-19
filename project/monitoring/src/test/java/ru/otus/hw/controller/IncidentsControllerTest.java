package ru.otus.hw.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.controller.rest.IncidentsController;
import ru.otus.hw.controller.rest.PageIncidentDto;
import ru.otus.hw.dto.DeviceDto;
import ru.otus.hw.dto.IncidentDto;
import ru.otus.hw.dto.ActionDto;
import ru.otus.hw.services.DeviceService;
import ru.otus.hw.services.IncidentService;
import ru.otus.hw.services.ActionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(IncidentsController.class)
@WithMockUser(authorities="ROLE_ADMIN")
@AutoConfigureMockMvc(addFilters = false)
class IncidentsControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private IncidentService incidentService;

    @MockitoBean
    private DeviceService deviceService;
    @MockitoBean
    private ActionService actionService;

    private List<IncidentDto> incidentDtos = List.of(new IncidentDto(1, "Fire!", new DeviceDto(), new ArrayList<ActionDto>()));

    @Test
    void shouldCorrectSaveNewIncident() throws Exception {
        IncidentDto expectedIncidentDto = incidentDtos.get(0);
        PageIncidentDto pageIncidentDto = new PageIncidentDto(null,"The Description", "ID_Kamera_1", 1L, "", 1L);
        when(incidentService.insert("The Description", "ID_Kamera_1")).thenReturn(expectedIncidentDto);
        when(incidentService.findById(1L)).thenReturn(Optional.of(expectedIncidentDto));


        mvc.perform(post("/api/v1/incident/")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(pageIncidentDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedIncidentDto)));
    }

    @Test
    void shouldGetAllIncidents() throws Exception {
        when(incidentService.findAll()).thenReturn(incidentDtos);
        mvc.perform(get("/api/v1/incident/")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(incidentDtos)));
    }

    @Test
    void shouldGetIncident() throws Exception {
        IncidentDto expectedIncidentDto = incidentDtos.get(0);
        when(incidentService.findById(1L)).thenReturn(Optional.of(expectedIncidentDto));
        mvc.perform(get("/api/v1/incident/1")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedIncidentDto)));
    }

    @Test
    void shouldDeleteIncident() throws Exception {
        IncidentDto expectedIncidentDto = incidentDtos.get(0);
        when(incidentService.findById(100L)).thenReturn(Optional.of(expectedIncidentDto));
        doNothing().when(incidentService).deleteById(100);
        mvc.perform(delete("/api/v1/incident/100"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted is success"));
        verify(incidentService, times(1)).deleteById(100L);
    }


}