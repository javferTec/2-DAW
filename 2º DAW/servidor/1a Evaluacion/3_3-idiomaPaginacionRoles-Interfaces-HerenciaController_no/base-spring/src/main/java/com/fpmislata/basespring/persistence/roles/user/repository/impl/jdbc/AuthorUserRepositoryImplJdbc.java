package com.fpmislata.basespring.persistence.roles.user.repository.impl.jdbc;

import com.fpmislata.basespring.domain.roles.user.model.AuthorUser;
import com.fpmislata.basespring.persistence.roles.user.repository.AuthorUserRepository;
import com.fpmislata.basespring.persistence.roles.user.repository.impl.jdbc.mapper.AuthorUserRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuthorUserRepositoryImplJdbc implements AuthorUserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<AuthorUser> getByIsbnBook(String isbn) {
        String sql = """
                    SELECT authors.* FROM authors
                                    JOIN books_authors ON authors.id = books_authors.author_id
                                    JOIN books ON books_authors.book_id = books.id
                                    AND books.isbn = ?
                """;
        return jdbcTemplate.query(sql, new AuthorUserRowMapper(), isbn);
    }
}
