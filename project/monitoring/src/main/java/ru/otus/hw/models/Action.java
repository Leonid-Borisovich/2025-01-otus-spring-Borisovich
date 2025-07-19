package ru.otus.hw.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "actions")
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "whatdo")
    private String whatDo;


   @Column(name = "incident_id")
   private long incidentId;

    public Action(String whatDo, long incidentId) {
        this.whatDo = whatDo;
        this.incidentId = incidentId;
    }

    @Override
    public String toString(){
        return "Id: %d, what done: %s, incident_id: %d".formatted(this.getId(), this.getWhatDo(), this.incidentId);
    }
}
