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



create table devices (
                         id bigserial,
                         full_name varchar(255),
                         primary key (id)
);

create table incidents (
                       id bigserial,
                       description varchar(255),
                       device_id bigint references devices (id) on delete cascade,
                       primary key (id)
);

create table actions
(
    id      bigserial,
    whatdo    varchar(1024),
    incident_id bigint references incidents (id) on delete cascade,
    primary key (id)
);