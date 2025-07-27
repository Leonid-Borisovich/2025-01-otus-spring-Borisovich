package ru.otus.hw.actuator;


import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import ru.otus.hw.services.IncidentService;

@Component
public class MonitoringIncidentsIndicator implements HealthIndicator {

    private final IncidentService incidentService;

    public MonitoringIncidentsIndicator(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    //@Secured("ROLE_HEAD")
    //@PreAuthorize("hasRole('ROLE_HEAD') ")
    @Override
    public Health health() {
        int size  = incidentService.findAll().size();
        if (size < 4) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("Emergency level", "В Багдаде все более-менее спокойно!")
                    .build();
        } else {
            return Health.up().withDetail("Emergency level", "Надо уже что-то делать!!").build();
        }
    }
}
