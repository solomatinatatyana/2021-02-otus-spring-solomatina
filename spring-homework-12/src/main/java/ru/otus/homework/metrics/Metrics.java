package ru.otus.homework.metrics;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Metrics {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Authors{
        public static final String GET_AUTHORS_REQ_TIME         = "library.get_authors.request";
        public static final String GET_AUTHOR_EDIT_REQ_TIME     = "library.get_author_edit.request";
        public static final String PATCH_AUTHOR_REQ_TIME        = "library.update_author.request";
        public static final String CREATE_AUTHOR_REQ_TIME       = "library.create_author.request";
        public static final String DELETE_AUTHOR_REQ_TIME       = "library.delete_author.request";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Genres{
        public static final String GET_GENRES_REQ_TIME         = "library.get_genres.request";
        public static final String GET_GENRE_EDIT_REQ_TIME     = "library.get_genre_edit.request";
        public static final String PATCH_GENRE_REQ_TIME        = "library.update_genre.request";
        public static final String CREATE_GENRE_REQ_TIME       = "library.create_genre.request";
        public static final String DELETE_GENRE_REQ_TIME       = "library.delete_genre.request";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Books{
        public static final String GET_BOOKS_REQ_TIME         = "library.get_books.request";
        public static final String GET_BOOK_ADD_REQ_TIME      = "library.get_book_add.request";
        public static final String GET_BOOK_EDIT_REQ_TIME     = "library.get_book_edit.request";
        public static final String PATCH_BOOK_REQ_TIME        = "library.update_book.request";
        public static final String CREATE_BOOK_REQ_TIME       = "library.create_book.request";
        public static final String DELETE_BOOK_REQ_TIME       = "library.delete_book.request";
    }
}
