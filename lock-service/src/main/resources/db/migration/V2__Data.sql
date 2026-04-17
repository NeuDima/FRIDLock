insert into rfid_lock.t_group (c_name)
values ('Вход 1'),
       ('Вход 2');

insert into rfid_lock.t_lock (c_name, c_secret, c_group_id)
VALUES ('Замок 1', 'Пароль 1', 1),
       ('Замок 2', 'Пароль 2', 2);

insert into rfid_lock.t_key (c_uid, c_type)
VALUES ('001', 'MASTER'),
       ('002', 'COMMON');

insert into rfid_lock.t_key_group (c_key_id, c_group_id)
values (1, 1),
       (1, 2);