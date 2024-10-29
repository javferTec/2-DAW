package com.fpmislata.basespring.persistence.roles.user.repository.impl.jdbc;

import com.fpmislata.basespring.domain.roles.user.model.BookUser;
import com.fpmislata.basespring.persistence.common.GenericRepository;
import com.fpmislata.basespring.persistence.common.JdbcHelper;
import com.fpmislata.basespring.persistence.common.SQLQueryGenerator;
import com.fpmislata.basespring.persistence.roles.user.repository.AuthorUserRepository;
import com.fpmislata.basespring.persistence.roles.user.repository.BookUserRepository;
import com.fpmislata.basespring.persistence.roles.user.repository.GenreUserRepository;
import com.fpmislata.basespring.persistence.roles.user.repository.impl.jdbc.mapper.BookUserRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookUserRepositoryImplJdbc implements GenericRepository<BookUser>, BookUserRepository {

    private final JdbcHelper jdbcHelper;
    private final AuthorUserRepository authorUserRepository;
    private final GenreUserRepository genreUserRepository;

    private static final String TABLE_NAME = "books";
    private static final String ISBN_COLUMN = "books.isbn";
    private static final String[][] JOINS = {
            {"categories", "books.category_id", "categories.id"},
            {"publishers", "books.publisher_id", "publishers.id"}
    };

    @Override
    public List<BookUser> getAll() {
        String sql = SQLQueryGenerator.getAll(TABLE_NAME);
        return jdbcHelper.getAll(sql, null, new BookUserRowMapper());
    }

    @Override
    public List<BookUser> getAll(int page, int size) {
        String baseQuery = SQLQueryGenerator.getAll(TABLE_NAME);
        String sql = SQLQueryGenerator.paginatedQuery(baseQuery, page, size);
        return jdbcHelper.getAll(sql, null, new BookUserRowMapper());
    }

    @Override
    public int count() {
        String sql = SQLQueryGenerator.count(TABLE_NAME);
        return jdbcHelper.count(sql);
    }

    @Override
    public Optional<BookUser> findByIsbn(String isbn) {
        String sql = SQLQueryGenerator.findWithJoins(TABLE_NAME, JOINS, ISBN_COLUMN);
        Optional<BookUser> bookUserOptional = jdbcHelper.findByColumn(sql, isbn, new BookUserRowMapper());
        bookUserOptional.ifPresent(bookUser -> {
            bookUser.setAuthorUsers(authorUserRepository.getByIsbnBook(isbn));
            bookUser.setGenreUsers(genreUserRepository.getByIsbnBook(isbn));
        });
        return bookUserOptional;
    }
}
