delete from rfid_lock.t_key_group;
delete from rfid_lock.t_lock;
delete from rfid_lock.t_key;
delete from rfid_lock.t_group;

insert into rfid_lock.t_key (id, c_uid, c_type)
values (1, '001', 'COMMON'),
       (2, '002', 'MASTER');

insert into rfid_lock.t_group (id, c_name)
values (1, 'Вход 1'),
       (2, 'Вход 2');

insert into rfid_lock.t_key_group (id, c_key_id, c_group_id)
values (1, 1, 1),
       (2, 2, 2);

insert into rfid_lock.t_lock (id, c_name, c_secret, c_group_id)
values (1, 'Lock 1', '123', 1),
       (2, 'Lock 2', '123', 2)