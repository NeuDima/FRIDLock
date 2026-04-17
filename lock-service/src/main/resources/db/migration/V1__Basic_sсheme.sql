create schema if not exists rfid_lock;

create table if not exists rfid_lock.t_group
(
    id     serial primary key,
    c_name varchar(256) not null unique
);

create table if not exists rfid_lock.t_lock
(
    id         serial primary key,
    c_name     varchar(128) not null unique,
    c_secret   varchar(128) not null,
    c_group_id int references rfid_lock.t_group (id) on delete cascade
);

create table if not exists rfid_lock.t_key
(
    id     serial primary key,
    c_uid varchar(128) not null unique,
    c_type varchar(128) not null
);

create table if not exists rfid_lock.t_key_group
(
    id         serial primary key,
    c_key_id   int references rfid_lock.t_key (id) on delete cascade,
    c_group_id int references rfid_lock.t_group (id) on delete cascade
);

create table if not exists rfid_lock.t_users
(
    id         serial primary key,
    c_name     varchar(128) not null unique,
    c_password varchar(128) not null
);