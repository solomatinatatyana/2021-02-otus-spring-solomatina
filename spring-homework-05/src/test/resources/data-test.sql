insert into genres(id, `name`) values (1,'Fantasy'),(2,'Roman');
insert into authors(id, fio) values (1,'Tolkien'),
                                    (2, 'Tolstoy');
insert into books (id, title, author_id, genre_id) values (1, 'The lord of the rings', 1, 1),
                                                          (2, 'War and peace', 2, 2);
insert into comments(id, comment_text, book_id) values (1, 'Great! rate 10', 1),
                                                      (2, 'Great! rate 5', 2),
                                                      (3, 'Super', 1),
                                                      (4, 'BAD BOOK', 2);