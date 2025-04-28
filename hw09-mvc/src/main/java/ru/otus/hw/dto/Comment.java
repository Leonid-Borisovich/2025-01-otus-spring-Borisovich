package ru.otus.hw.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private long id;

    private String text;

    private long bookId;

    @Override
    public String toString(){
        return "Id: %d, text of comment: %s, book_id: %d".formatted(this.getId(), this.getText(), this.bookId);
    }
}
