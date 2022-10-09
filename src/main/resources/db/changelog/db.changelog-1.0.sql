-- Создание Авторов
create table if not exists author
(
    id      bigserial primary key,
    name    varchar(255),
    surname varchar(255)
);

-- Создание Пользователей
create table if not exists usr
(
    id       bigserial primary key,
    username varchar(255),
    password varchar(255),
    active   boolean
);

-- Создание Книг
create table if not exists book
(
    id        bigserial primary key,
    title     varchar(255),
    year      int,
    author_id bigint references author (id),
    user_id   bigint references usr (id)
);
