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

create table action_types
(
    id      bigserial,
    name    varchar(128),
    primary key (id)
);


create table devices (
                         id varchar(32),
                         full_name varchar(255),
                         latitude numeric(8,4),
                         longitude numeric(8,4),
                         primary key (id)
);

create table incidents (
                       id bigserial,
                       description varchar(255),
                       device_id varchar(32) references devices (id) on delete cascade,
                       primary key (id)
);

create table actions
(
    id      bigserial,
    whatdo    varchar(1024),
    incident_id bigint references incidents (id) on delete cascade,
    action_type_id bigint not null references action_types (id) on delete cascade,
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
