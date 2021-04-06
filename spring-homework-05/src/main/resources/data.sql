insert into genres(id, `name`) values (1,'Фантастика'),
                                      (2, 'Роман');
insert into authors(id, fio) values (1,'Толкиен'),
                                    (2, 'Толстой');
insert into books (id, title, author_id, genre_id) values (1, 'Властелин колец', 1, 1),
                                                          (2, 'Война и мир', 2, 2);