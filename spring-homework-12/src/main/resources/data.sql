
insert into genres(`name`) values ('Фантастика'),
                                      ('Роман');
insert into authors(fio) values ('Джон Толкин'),
                                    ('Лев Толстой'),
                                    ('Роберт Желязны');
insert into books (title, author_id, genre_id) values ('Властелин колец', 1, 1),
                                                          ('Война и мир', 2, 2),
                                                          ('Хоббит или туда и обратно',1,1),
                                                          ('Девять принцев Амбера',3,1);
insert into comments(comment_text, book_id) values ('Отличная книга. 10 из 10', 1),
                                                      ('Нормальная. 5 из 10', 2),
                                                      ('Хорошая книга. 8 из 10', 1),
                                                      ('Выше среденего. 6 из 10', 2),
                                                      ('Супер', 3),
                                                      ('Понравилась',4);

insert into library_users(username, password, email, active, roles) values
                                                    ('admin', 'password', 'admin@mail.ru', true, 'ADMIN'),
                                                    ('user','password', 'user@mail.ru', true, 'USER');


insert into authorities(username, roles) values ('admin', 'ADMIN'),
                                            ('user','USER');


insert into acl_sid (id, principal, sid) values
                                             (1, 0, 'ROLE_ADMIN'),
                                             (2, 0, 'ROLE_USER');

insert into acl_class (id, class) values (1, 'ru.otus.homework.domain.Book');

insert into acl_object_identity (id, object_id_class, object_id_identity,
                                 parent_object, owner_sid, entries_inheriting)
values
    (1, 1, 1, NULL, 1, 0),
    (2, 1, 2, NULL, 1, 0),
    (3, 1, 3, NULL, 1, 0),
    (4, 1, 4, NULL, 1, 0);


insert into acl_entry (id, acl_object_identity, ace_order, sid, mask,
                       granting, audit_success, audit_failure)
values
    --books (1)
    --admin
    (1, 1, 1, 1, 1, 1, 1, 1),
    (2, 1, 2, 1, 2, 1, 1, 1),
    --user
    (3, 1, 3, 2, 1, 1, 1, 1),
    ----books (2)
    --admin
    (4, 2, 1, 1, 1, 1, 1, 1),
    (5, 2, 2, 1, 2, 1, 1, 1),
    --user
    (6, 2, 3, 2, 1, 1, 1, 1),
    ----books (3)
    --admin
    (7, 3, 1, 1, 1, 1, 1, 1),
    (8, 3, 2, 1, 2, 1, 1, 1),
    --user
    (9, 3, 3, 2, 1, 1, 1, 1),
    ----books (4)
    --admin
    (10, 4, 1, 1, 1, 1, 1, 1),
    (11, 4, 2, 1, 2, 1, 1, 1),
    --user
    (12, 4, 3, 2, 1, 1, 1, 1);

