package com.fpmislata.basespring.persistence.repository.impl.jdbc;

import com.fpmislata.basespring.domain.model.Genre;
import com.fpmislata.basespring.persistence.repository.GenreRepository;
import com.fpmislata.basespring.persistence.repository.impl.jdbc.mapper.GenreRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryImplJdbc implements GenreRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getByIsbnBook(String isbn) {
        String sql = """
                SELECT genres.* FROM genres
                                JOIN books_genres ON genres.id = books_genres.genre_id
                                JOIN books ON books_genres.book_id = books.id
                                AND books.isbn = ?
            """;
        return jdbcTemplate.query(sql, new GenreRowMapper(), isbn);
    }
}
