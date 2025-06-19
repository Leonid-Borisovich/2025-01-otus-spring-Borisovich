package ru.otus.hw.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.stream.IntStream;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "borisovich", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertBooks", author = "borisovich")
    public void insertBooks(BookRepository bRepo, AuthorRepository aRepo, GenreRepository gRepo) {
        var a1 = aRepo.save(new Author("_a1", "Author_1"));
        var a2 = aRepo.save(new Author("_a2", "Author_2"));
        var a3 = aRepo.save(new Author("_a3", "Author_3"));

        var g1 = gRepo.save(new Genre("_g1", "Genre_1"));
        var g2 = gRepo.save(new Genre("_g2", "Genre_2"));
        var g3 = gRepo.save(new Genre("_g3", "Genre_3"));


        var b1 = bRepo.save(new Book("_b1", "BookTitle_1",  a1, g1));
        b1.setComments(IntStream.range(1, 4).boxed()
                .map(id -> new Comment("_c" + id, "text_of_comment_" + id))
                .toList());

        bRepo.save(b1);
        bRepo.save(new Book("_b2", "BookTitle_2",  a2, g2));
        bRepo.save(new Book("_b3", "BookTitle_3",  a3, g3));
    }

}
