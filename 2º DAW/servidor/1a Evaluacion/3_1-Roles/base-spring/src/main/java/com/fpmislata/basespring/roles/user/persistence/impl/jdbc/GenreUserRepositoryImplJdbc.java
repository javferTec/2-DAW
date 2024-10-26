package com.fpmislata.basespring.roles.user.persistence.impl.jdbc;

import com.fpmislata.basespring.roles.user.domain.model.GenreUser;
import com.fpmislata.basespring.roles.user.domain.repository.GenreUserRepository;
import com.fpmislata.basespring.roles.user.persistence.impl.jdbc.mapper.GenreUserRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GenreUserRepositoryImplJdbc implements GenreUserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<GenreUser> getByIsbnBook(String isbn) {
        String sql = """
                SELECT genreUsers.* FROM genreUsers
                                JOIN books_genres ON genreUsers.id = books_genres.genre_id
                                JOIN books ON books_genres.book_id = books.id
                                AND books.isbn = ?
            """;
        return jdbcTemplate.query(sql, new GenreUserRowMapper(), isbn);
    }
}
