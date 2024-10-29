package com.fpmislata.basespring.persistence.roles.admin.repository.impl.jdbc;


import com.fpmislata.basespring.persistence.common.JdbcHelper;
import com.fpmislata.basespring.persistence.common.SQLQueryGenerator;
import com.fpmislata.basespring.domain.roles.admin.model.BookAdmin;
import com.fpmislata.basespring.persistence.roles.admin.repository.AuthorAdminRepository;
import com.fpmislata.basespring.persistence.roles.admin.repository.BookAdminRepository;
import com.fpmislata.basespring.persistence.roles.admin.repository.GenreAdminRepository;
import com.fpmislata.basespring.persistence.roles.admin.repository.impl.jdbc.mapper.BookAdminRowMapper;
import com.fpmislata.basespring.persistence.common.GenericRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookAdminRepositoryImplJdbc implements GenericRepository<BookAdmin>, BookAdminRepository {

    private final JdbcHelper jdbcHelper;

    private final AuthorAdminRepository authorAdminRepository;
    private final GenreAdminRepository genreAdminRepository;

    private static final String TABLE_NAME = "books";
    private static final String ISBN_COLUMN = "books.isbn";
    private static final String[][] JOINS = {
            {"categories", "books.category_id", "categories.id"},
            {"publishers", "books.publisher_id", "publishers.id"}
    };

    @Override
    public List<BookAdmin> getAll() {
        String sql = SQLQueryGenerator.getAll(TABLE_NAME);
        return jdbcHelper.getAll(sql, null, new BookAdminRowMapper());
    }

    @Override
    public List<BookAdmin> getAll(int page, int size) {
        String baseQuery = SQLQueryGenerator.getAll(TABLE_NAME);
        String sql = SQLQueryGenerator.paginatedQuery(baseQuery, page, size);
        return jdbcHelper.getAll(sql, null, new BookAdminRowMapper());
    }

    @Override
    public int count() {
        String sql = SQLQueryGenerator.count(TABLE_NAME);
        return jdbcHelper.count(sql);
    }

    @Override
    public Optional<BookAdmin> findByIsbn(String isbn) {
        String sql = SQLQueryGenerator.findWithJoins(TABLE_NAME, JOINS, ISBN_COLUMN);
        Optional<BookAdmin> bookAdminOptional = jdbcHelper.findByColumn(sql, isbn, new BookAdminRowMapper());
        bookAdminOptional.ifPresent(bookAdmin -> {
            bookAdmin.setAuthorAdmins(authorAdminRepository.getByIsbnBook(isbn));
            bookAdmin.setGenreAdmins(genreAdminRepository.getByIsbnBook(isbn));
        });
        return bookAdminOptional;
    }
}