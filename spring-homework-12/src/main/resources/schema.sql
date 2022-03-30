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
    active boolean,
    roles varchar (50)
);

create table authorities(
    username varchar (50) references library_users(username),
    roles varchar (50)
);

CREATE TABLE IF NOT EXISTS acl_sid (
    id bigint(20) NOT NULL AUTO_INCREMENT primary key,
    principal tinyint(1) NOT NULL,
    sid varchar(100) NOT NULL,
    UNIQUE KEY unique_uk_1 (sid,principal)
    );

CREATE TABLE IF NOT EXISTS acl_class (
    id bigint(20) NOT NULL AUTO_INCREMENT primary key,
    class varchar(255) NOT NULL,
    UNIQUE KEY unique_uk_2 (class)
    );

CREATE TABLE IF NOT EXISTS acl_object_identity (
    id bigint(20) NOT NULL AUTO_INCREMENT primary key,
    object_id_class bigint(20) NOT NULL,
    object_id_identity bigint(20) NOT NULL,
    parent_object bigint(20) DEFAULT NULL,
    owner_sid bigint(20) DEFAULT NULL,
    entries_inheriting tinyint(1) NOT NULL,
    UNIQUE KEY unique_uk_3 (object_id_class,object_id_identity)
    );

ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id);
ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (object_id_class) REFERENCES acl_class (id);
ALTER TABLE acl_object_identity
    ADD FOREIGN KEY (owner_sid) REFERENCES acl_sid (id);

CREATE TABLE IF NOT EXISTS acl_entry (
    id bigint(20) NOT NULL AUTO_INCREMENT primary key,
    acl_object_identity bigint(20) NOT NULL,
    ace_order int(11) NOT NULL,
    sid bigint(20) NOT NULL,
    mask int(11) NOT NULL,
    granting tinyint(1) NOT NULL,
    audit_success tinyint(1) NOT NULL,
    audit_failure tinyint(1) NOT NULL,
    UNIQUE KEY unique_uk_4 (acl_object_identity,ace_order)
    );

ALTER TABLE acl_entry
    ADD FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity(id);
ALTER TABLE acl_entry
    ADD FOREIGN KEY (sid) REFERENCES acl_sid(id);

