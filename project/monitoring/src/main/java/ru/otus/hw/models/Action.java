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

    @Column(name = "action_type_id")
    private long actionTypeId;


    public Action(String whatDo, long incidentId, long actionTypeId) {
        this.whatDo = whatDo;
        this.incidentId = incidentId;
        this.actionTypeId = actionTypeId;
    }

    @Override
    public String toString(){
        return "Id: %d, what done: %s, incident_id: %d".formatted(this.getId(), this.getWhatDo(), this.incidentId);
    }
}
