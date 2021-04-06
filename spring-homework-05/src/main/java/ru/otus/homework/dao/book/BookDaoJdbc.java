package ru.otus.homework.dao.book;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BookDaoJdbc implements BookDao{
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public void insert(Book book){
        namedParameterJdbcOperations.update(
                "insert into books (id, title, author_id, genre_id) values (:id, :title, :author_id, :genre_id)",
                Map.of(
                        "id", book.getId(),
                        "title", book.getTitle(),
                        "author_id", book.getAuthorId(),
                        "genre_id", book.getGenreId()));
    }

    @Override
    public void updateTitleById(long id, String title) {
        namedParameterJdbcOperations.update("update books set title = :title where id = :id",
                Map.of("id", id, "title", title));
    }

    @Override
    public Optional<Book> findByName(String title){
        Map<String, Object> params = Collections.singletonMap("title", title);
        return Optional.of(namedParameterJdbcOperations.queryForObject(
                "select * from books where title = :title", params, new BookMapper()));
    }

    @Override
    public Optional<Book> findById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return Optional.of(namedParameterJdbcOperations.queryForObject(
                "select * from books where id = :id", params, new BookMapper()));
    }

    @Override
    public List<Book> findAll(){
        return namedParameterJdbcOperations.query("select * from books", new BookMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update("delete from books where id = :id", params);
    }

    @Override
    public void deleteByName(String title){
        Map<String, Object> params = Collections.singletonMap("title", title);
        namedParameterJdbcOperations.update("delete from books where title = :title", params);
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String title = resultSet.getString("title");
            long author_id = resultSet.getLong("author_id");
            long genre_id = resultSet.getLong("genre_id");
            return new Book(id, title, author_id, genre_id);
        }
    }
}
