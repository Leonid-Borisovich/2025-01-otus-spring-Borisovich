create table authors (
    id bigserial,
    full_name varchar(255),
    primary key (id)
);

create table genres (
    id bigserial,
    name varchar(255),
    primary key (id)
);

create table books (
    id bigserial,
    title varchar(255),
    author_id bigint references authors (id) on delete cascade,
    genre_id bigint references genres(id) on delete cascade,
    primary key (id)
);

create table comments
(
    id      bigserial,
    text    varchar(1024),
    book_id bigint references books (id) on delete cascade,
    primary key (id)
);

create table users (
                       user_id bigserial  primary key,
                       username varchar(50) not null,
                       password varchar(100) not null
);

create table roles (
                       role_id bigserial,
                       role varchar(50) not null,
                       primary key (role_id)

);

CREATE TABLE user_roles (
                            user_id BIGINT NOT NULL,
                            role_id BIGINT NOT NULL,
                            PRIMARY KEY (user_id, role_id),
                            FOREIGN KEY (user_id) REFERENCES users(user_id),
                            FOREIGN KEY (role_id) REFERENCES roles(role_id)
);
