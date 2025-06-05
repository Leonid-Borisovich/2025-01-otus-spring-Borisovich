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


insert into users(user_id, username, password)
values (1, 'alisa', '$2a$10$19PJLj3eBq/EVYxdhOW1B.5bzaIkwIB1oHz3b9pyuiiVi3D5DgOHC'),
       (2, 'bob', '$2a$10$k91JXxzRWKPQN2irtPSHs.t1Yi3MGO0/xyUpekl4w8Cl0Bl8o3cWS'),
       (3, 'tom', '$2a$10$pS.ApBtqAGSHbUpi0rPQw.LZBOroPoB2x1VtelzqHjCH5K8huejiq');

insert into roles(role_id, role)
values (10, 'GUEST'),
       (20, 'WRITER'),
       (30, 'ADMIN');

insert into user_roles(user_id, role_id)
values (1, 10),
       (3, 10), (3, 20), (3, 30),
       (2, 10), (2, 20);
