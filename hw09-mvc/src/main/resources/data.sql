insert into authors(full_name)
values ('Агата Кристи'), ('О.Генри'), ('Джек Лондон');

insert into genres(name)
values ('Детектив'), ('Комедия'), ('Приключения');

insert into books(title, author_id, genre_id)
values ('10 негритят', 1, 1), ('Короли и капуста', 2, 2), ('Зов предков', 3, 3);

insert into comments(text, book_id)
values ('Агата Кристи - это классика детектива! Советую!', 1),
       ('А какого современного автора посоветуйте?', 1),
       ('Почитайте Б.Акунина!', 1);
