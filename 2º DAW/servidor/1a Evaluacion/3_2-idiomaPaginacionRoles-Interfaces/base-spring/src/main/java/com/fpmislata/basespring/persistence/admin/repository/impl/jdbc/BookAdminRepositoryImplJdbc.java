package com.fpmislata.basespring.persistence.admin.repository.impl.jdbc;


import com.fpmislata.basespring.domain.admin.model.BookAdmin;
import com.fpmislata.basespring.persistence.admin.repository.AuthorAdminRepository;
import com.fpmislata.basespring.persistence.admin.repository.BookAdminRepository;
import com.fpmislata.basespring.persistence.admin.repository.GenreAdminRepository;
import com.fpmislata.basespring.persistence.admin.repository.impl.jdbc.mapper.BookAdminRowMapper;
import com.fpmislata.basespring.persistence.common.generic.GenericRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookAdminRepositoryImplJdbc implements GenericRepository<BookAdmin>, BookAdminRepository {

    private final JdbcTemplate jdbcTemplate;

    private final AuthorAdminRepository authorAdminRepository;
    private final GenreAdminRepository genreAdminRepository;

    @Override
    public List<BookAdmin> getAll() {
        String sql = "SELECT * FROM books";
        return jdbcTemplate.query(sql, new BookAdminRowMapper());
    }

    @Override
    public List<BookAdmin> getAll(int page, int size) {
        String sql = "SELECT * FROM books LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new BookAdminRowMapper(), size, page * size);
    }

    @Override
    public int count() {
        String sql = "SELECT COUNT(*) FROM books";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public Optional<BookAdmin> findByIsbn(String isbn) {
        String sql = """
                SELECT * FROM books
                LEFT JOIN categories ON books.category_id = categories.id
                LEFT JOIN publishers ON books.publisher_id = publishers.id
                WHERE books.isbn = ?
           """;
        try {
            BookAdmin bookAdmin = jdbcTemplate.queryForObject(sql, new BookAdminRowMapper(), isbn);
            bookAdmin.setAuthorAdmins(authorAdminRepository.getByIsbnBook(isbn));
            bookAdmin.setGenreAdmins(genreAdminRepository.getByIsbnBook(isbn));
            return Optional.of(bookAdmin);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}