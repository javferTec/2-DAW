package com.fpmislata.basespring.roles.admin.persistence.impl.jdbc;

import com.fpmislata.basespring.roles.admin.domain.model.AuthorAdmin;
import com.fpmislata.basespring.roles.admin.domain.repository.AuthorAdminRepository;
import com.fpmislata.basespring.roles.admin.persistence.impl.jdbc.mapper.AuthorAdminRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuthorAdminRepositoryImpl implements AuthorAdminRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<AuthorAdmin> getByIsbnBook(String isbn) {
        String sql = """
                SELECT authors.* FROM authors
                JOIN books_authors ON authors.id = books_authors.author_id
                JOIN books ON books_authors.book_id = books.id
                AND books.isbn = ?
           """;
        return jdbcTemplate.query(sql, new AuthorAdminRowMapper(), isbn);
    }
}
