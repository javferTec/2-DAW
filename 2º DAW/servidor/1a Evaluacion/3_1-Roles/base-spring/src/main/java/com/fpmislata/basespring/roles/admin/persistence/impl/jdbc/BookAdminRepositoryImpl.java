package com.fpmislata.basespring.roles.admin.persistence.impl.jdbc;

import com.fpmislata.basespring.roles.admin.domain.model.BookAdmin;
import com.fpmislata.basespring.roles.admin.domain.repository.AuthorAdminRepository;
import com.fpmislata.basespring.roles.admin.domain.repository.BookAdminRepository;
import com.fpmislata.basespring.roles.admin.domain.repository.GenreAdminRepository;
import com.fpmislata.basespring.roles.admin.persistence.impl.jdbc.mapper.BookAdminRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookAdminRepositoryImpl implements BookAdminRepository {

    private final JdbcTemplate jdbcTemplate;

    private final AuthorAdminRepository authorAdminRepository;
    private final GenreAdminRepository genreAdminRepository;

    @Override
    public List<BookAdmin> findAll() {
        String sql = """
                        SELECT * FROM books
                     """;
        return jdbcTemplate.query(sql, new BookAdminRowMapper());
    }

    @Override
    public List<BookAdmin> findAll(int page, int size) {
        String sql = """
                        SELECT * FROM books
                        LIMIT ? OFFSET ?
                     """;
        return jdbcTemplate.query(sql, new BookAdminRowMapper(), size, page * size);
    }

    @Override
    public int count() {
        String sql = """
                        SELECT COUNT(*) FROM books
                     """;
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public Optional<BookAdmin> findByIsbn(String isbn) {
        String sql = """
                SELECT * FROM books
                LEFT JOIN categories ON books.category_id = categories.id
                LEFT JOIN publisherUsers ON books.publisher_id = publisherUsers.id
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