package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.hw.models.Incident;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "incidents")
public interface IncidentRepository extends JpaRepository<Incident, Long> {

    @Query("select i from Incident i left join fetch i.actions left join fetch i.device")
    List<Incident> findAll();

    @Query("select i from Incident i " +
            "left join fetch i.actions left join fetch i.device where i.id= :id")
    Optional<Incident>findById(@Param("id") Long id);
}
