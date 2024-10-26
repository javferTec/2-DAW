package com.fpmislata.basespring.roles.user.persistence.impl.jdbc;

import com.fpmislata.basespring.roles.user.domain.model.AuthorUser;
import com.fpmislata.basespring.roles.user.domain.repository.AuthorUserRepository;
import com.fpmislata.basespring.roles.user.persistence.impl.jdbc.mapper.AuthorUserRowMapper;
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
                    SELECT authorUsers.* FROM authorUsers
                                    JOIN books_authors ON authorUsers.id = books_authors.author_id
                                    JOIN books ON books_authors.book_id = books.id
                                    AND books.isbn = ?
                """;
        return jdbcTemplate.query(sql, new AuthorUserRowMapper(), isbn);
    }
}
