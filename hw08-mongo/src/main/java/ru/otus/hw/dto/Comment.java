package ru.otus.hw.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private String id;

    private String text;

    @Override
    public String toString(){
        return "Id: %s, text of comment: %s".formatted(this.getId(), this.getText());
    }
}
