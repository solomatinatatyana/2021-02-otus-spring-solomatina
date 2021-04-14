package ru.otus.homework.dao.author;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class AuthorDaoJdbc implements AuthorDao{
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public AuthorDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public void insert(Author author) {
        namedParameterJdbcOperations.update(
                "insert into authors (id, fio) values (:id, :fio)",
                Map.of(
                        "id", author.getId(),
                        "fio", author.getFullName()));
    }

    @Override
    public Optional<Author> findById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return Optional.of(namedParameterJdbcOperations.queryForObject(
                "select id, fio from authors where id = :id", params, new AuthorMapper()));
    }

    @Override
    public List<Author> findAll() {
        return namedParameterJdbcOperations.query("select id, fio from authors", new AuthorMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update("delete from authors where id = :id", params);
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String fio = resultSet.getString("fio");
            return new Author(id, fio);
        }
    }

}
