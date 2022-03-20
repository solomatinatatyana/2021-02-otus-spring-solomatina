
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

insert into library_users(username, password, email, active) values
                                                    ('admin', 'password', 'admin@mail.ru', true),
                                                    ('user','password', 'user@mail.ru', true);


insert into authorities(username, roles) values ('admin', 'ROLE_ADMIN'),
                                            ('user','ROLE_USER');