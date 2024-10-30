package com.fpmislata.basespring.persistence.repository.impl.jdbc;

import com.fpmislata.basespring.domain.model.Author;
import com.fpmislata.basespring.domain.repository.AuthorRepository;
import com.fpmislata.basespring.persistence.repository.impl.jdbc.common.JdbcOperations;
import com.fpmislata.basespring.persistence.repository.impl.jdbc.common.SqlBuilder;
import com.fpmislata.basespring.persistence.repository.impl.jdbc.mapper.AuthorRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryJdbc implements AuthorRepository {

    private static final String TABLE_NAME = "authors";
    private static final String ID_COLUMN = "id";
    private static final String ISBN_COLUMN = "books.isbn";
    private static final String[][] JOIN_CLAUSES = {
            {"books_authors", "authors.id", "books_authors.author_id"},
            {"books", "books_authors.book_id", "books.id"}
    };
    private final JdbcOperations jdbcOperations;

    @Override
    public List<Author> getByIsbnBook(String isbn) {
        String sql = SqlBuilder.findWithJoins(TABLE_NAME, JOIN_CLAUSES, ISBN_COLUMN);
        return jdbcOperations.getAll(sql, new Object[]{isbn}, new AuthorRowMapper());
    }

    @Override
    public List<Author> getByIdBook(long idBook) {
        String sql = SqlBuilder.findWithJoins(TABLE_NAME, JOIN_CLAUSES, ID_COLUMN);
        return jdbcOperations.getAll(sql, new Object[]{idBook}, new AuthorRowMapper());
    }

    @Override
    public List<Author> findAllById(Long[] ids) {
        String sql = SqlBuilder.findByColumn(TABLE_NAME, ID_COLUMN);
        Map<String, List<Long>> params = Map.of("ids", Arrays.asList(ids));
        return jdbcOperations.getAll(sql, params.values().toArray(), new AuthorRowMapper());
    }
}