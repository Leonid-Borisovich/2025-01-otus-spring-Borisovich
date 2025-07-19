package ru.otus.hw.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionDto {

    private long id;

    private String whatDo;

    private long incidentId;

    @Override
    public String toString(){
        return "Id: %d, done: %s, incident_id: %d".formatted(this.getId(), this.getWhatDo(), this.incidentId);
    }
}
