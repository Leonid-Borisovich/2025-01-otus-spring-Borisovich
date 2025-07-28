insert into action_types(id, name)
values (1, 'Ложная тревога'),
       (2, 'Локальное происшествие'),
       (3, 'Пожарная тревога'),
       (4, 'Боевая тревога, гоблины атакуют');


insert into devices(id, full_name)
values ('ID_Kamera_1', 'Kamera_1'), ('ID_Kamera_2', 'Kamera_2'), ('ID_Kamera_3', 'Kamera_3');


insert into incidents(description, device_id)
values ('incident_1', 'ID_Kamera_1'), ('incident_2', 'ID_Kamera_1'), ('incident_3', 'ID_Kamera_1');

insert into actions(whatdo, incident_id, action_type_id)
values ('what_do_1', 1, 1), ('what_do_2', 1, 1), ('what_do_3', 1, 1);


