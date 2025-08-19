package ru.otus.hw.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "incidents")
@EqualsAndHashCode
@ToString
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String description;

    @EqualsAndHashCode.Exclude
    @ManyToOne(targetEntity = Device.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private Device device;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(targetEntity = Action.class,
            fetch = FetchType.LAZY,
            cascade = {CascadeType.REMOVE},
            orphanRemoval = true)
    @JoinColumn(name = "incident_id")
    private List<Action> actions;

    public Incident(long id, String description, Device device) {
        this.id = id;
        this.description = description;
        this.device = device;
        this.actions = new ArrayList<>();
    }
}
