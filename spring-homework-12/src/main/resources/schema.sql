DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS genres;

create table authors(
    id bigserial AUTO_INCREMENT,
    fio varchar (8000),
    primary key (id)
);

create table genres(
    id bigserial AUTO_INCREMENT,
    name varchar (255),
    primary key (id)
);

create table books(
    id bigint AUTO_INCREMENT primary key,
    title varchar (255),
    author_id bigint references authors(id) on delete cascade,
    genre_id bigint references genres(id) on delete cascade
);
create table comments(
    id bigserial AUTO_INCREMENT primary key,
    comment_text varchar (255),
    book_id bigint references books(id) on delete cascade
);

create table library_users(
    id bigserial AUTO_INCREMENT primary key,
    username varchar (50),
    password varchar (100),
    email varchar (100),
    active boolean
);

create table authorities(
    username varchar (50) references library_users(username),
    roles varchar (50)
);