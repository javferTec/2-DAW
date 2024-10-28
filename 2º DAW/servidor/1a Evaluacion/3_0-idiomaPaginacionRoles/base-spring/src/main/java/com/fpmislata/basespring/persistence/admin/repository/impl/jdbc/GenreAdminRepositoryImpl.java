package com.fpmislata.basespring.persistence.admin.repository.impl.jdbc;


import com.fpmislata.basespring.domain.admin.model.GenreAdmin;
import com.fpmislata.basespring.persistence.admin.repository.GenreAdminRepository;
import com.fpmislata.basespring.persistence.admin.repository.impl.jdbc.mapper.GenreAdminRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GenreAdminRepositoryImpl implements GenreAdminRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<GenreAdmin> getByIsbnBook(String isbn) {
        String sql = """
                SELECT genres.* FROM genres
                JOIN books_genres ON genres.id = books_genres.genre_id
                JOIN books ON books_genres.book_id = books.id
                AND books.isbn = ?
           """;
        return jdbcTemplate.query(sql, new GenreAdminRowMapper(),isbn);
    }
}
