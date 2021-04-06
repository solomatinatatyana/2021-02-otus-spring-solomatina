insert into genres(id, `name`) values (1,'Fantasy'),(2,'Roman');
insert into authors(id, fio) values (1,'Tolkien'),
                                    (2, 'Tolstoy');
insert into books (id, title, author_id, genre_id) values (1, 'The lord of the rings', 1, 1),
                                                          (2, 'War and peace', 2, 2);