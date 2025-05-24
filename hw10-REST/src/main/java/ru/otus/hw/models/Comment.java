package ru.otus.hw.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String text;


   @Column(name = "book_id")
   private long bookId;

    public Comment(String text, long bookId) {
        this.text = text;
        this.bookId = bookId;
    }

    @Override
    public String toString(){
        return "Id: %d, text of comment: %s, book_id: %d".formatted(this.getId(), this.getText(), this.bookId);
    }
}
