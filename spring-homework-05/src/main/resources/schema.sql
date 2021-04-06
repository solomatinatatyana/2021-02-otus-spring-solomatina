DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS genres;

create table authors(
    id bigint,
    fio varchar (8000),
    primary key (id)
);

create table genres(
    id bigint,
    name varchar (255),
    primary key (id)
);

create table books(
    id bigint primary key,
    title varchar (255),
    author_id bigint references authors(id) on delete cascade,
    genre_id bigint references genres(id) on delete cascade
);