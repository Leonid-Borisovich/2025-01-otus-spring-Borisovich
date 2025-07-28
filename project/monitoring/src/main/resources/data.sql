insert into devices(id, full_name, latitude, longitude)
values ('ID_Южные_ворота', 'Южные ворота', 73.365, 50.412),
       ('ID_Северная_стена', 'Северная стена', 73.366, 50.413),
       ('ID_Подъемный_мост', 'Подъемный мост', 73.367, 50.414);

insert into incidents(description, device_id)
values ('Пересечение охраняемого периметра', 'ID_Южные_ворота'),
       ('Открытый огонь', 'ID_Северная_стена'),
       ('Скопление гоблинов', 'ID_Подъемный_мост');

insert into action_types(id, name)
values (1, 'Ложная тревога'),
       (2, 'Локальное происшествие'),
       (3, 'Пожарная тревога'),
       (4, 'Боевая тревога, гоблины атакуют');

insert into actions(whatdo, incident_id, action_type_id)
values ('Отправлен патруль для проверки', 1, 2),
       ('Ложная тревога, обнаружены животные', 1, 1),
       ('Причины возгорания выясняются', 2, 3),
       ('Застава в ружье', 3, 4);


insert into users(user_id, username, password)
values (1, 'operator', '$2a$10$x7pevcRqsFHCFg8T1thcoej7veMaLuFj/8HgACiPYSMTuuvoIB9xi'),
       (2, 'head', '$2a$10$Z6LPRsMDs7yM.mFjxp/US.bdVL6s5nkpHBEKnrGJ6n3Mptw5bOwp2'),
       (3, 'admin', '$2a$10$NoZvA5lVLNDG.y4F48lRgu4TQtwnKkR3CYfgr7oD2s8IFzBY208ua');

insert into roles(role_id, role)
values (10, 'ROLE_OPERATOR'),
       (20, 'ROLE_HEAD'),
       (30, 'ROLE_ADMIN');

insert into user_roles(user_id, role_id)
values (1, 10),
       (3, 30),
       (2, 20);
