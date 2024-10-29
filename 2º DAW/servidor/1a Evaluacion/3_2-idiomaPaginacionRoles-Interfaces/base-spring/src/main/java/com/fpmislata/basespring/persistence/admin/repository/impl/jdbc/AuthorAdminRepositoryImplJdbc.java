package com.fpmislata.basespring.persistence.admin.repository.impl.jdbc;


import com.fpmislata.basespring.domain.admin.model.AuthorAdmin;
import com.fpmislata.basespring.persistence.admin.repository.AuthorAdminRepository;
import com.fpmislata.basespring.persistence.admin.repository.impl.jdbc.mapper.AuthorAdminRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuthorAdminRepositoryImplJdbc implements AuthorAdminRepository {

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
