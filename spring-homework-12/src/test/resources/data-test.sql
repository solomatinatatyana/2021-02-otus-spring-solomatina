insert into genres(`name`) values ('Fantasy'),('Roman');
insert into authors(fio) values ('Tolkien'),
                                    ('Tolstoy');
insert into books (title, author_id, genre_id) values ('The lord of the rings', 1, 1),
                                                          ('War and peace', 2, 2);
insert into comments(comment_text, book_id) values ('Great! rate 10', 1),
                                                      ('Great! rate 5', 2),
                                                      ('Super', 1),
                                                      ('BAD BOOK', 2);

