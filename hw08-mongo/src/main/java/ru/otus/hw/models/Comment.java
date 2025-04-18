package ru.otus.hw.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "comments")
public class Comment {

    @Id
    private String id = UUID.randomUUID().toString();

    private String text;

    public Comment(String text) {
        this.text = text;
    }

    @Override
    public String toString(){
        return "Id: %s, text of comment: %s".formatted(this.getId(), this.getText());
    }
}
