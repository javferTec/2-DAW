package com.fpmislata.basespring.roles.user.persistence.impl.jdbc;

import com.fpmislata.basespring.roles.user.domain.model.BookUser;
import com.fpmislata.basespring.roles.user.domain.repository.AuthorUserRepository;
import com.fpmislata.basespring.roles.user.domain.repository.BookUserRepository;
import com.fpmislata.basespring.roles.user.domain.repository.GenreUserRepository;
import com.fpmislata.basespring.roles.user.persistence.impl.jdbc.mapper.BookUserRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookUserRepositoryImplJdbc implements BookUserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final AuthorUserRepository authorUserRepository;
    private final GenreUserRepository genreUserRepository;

    @Override
    public List<BookUser> findAll() {
        String sql = "SELECT * FROM books";
        return jdbcTemplate.query(sql, new BookUserRowMapper());
    }

    @Override
    public List<BookUser> findAll(int page, int size) {
        String sql = "SELECT * FROM books LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new BookUserRowMapper(), size, page * size);
    }

    @Override
    public int count() {
        String sql = "SELECT COUNT(*) FROM books";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return (count != null) ? count : 0;
    }

    @Override
    public Optional<BookUser> findByIsbn(String isbn) {
        String sql = """
                SELECT * FROM books
                LEFT JOIN categories ON books.category_id = categories.id
                LEFT JOIN publisherUsers ON books.publisher_id = publisherUsers.id
                WHERE books.isbn = ?
           """;
        try {
            BookUser bookUser = jdbcTemplate.queryForObject(sql, new BookUserRowMapper(), isbn);
            assert bookUser != null;
            bookUser.setAuthorUsers(authorUserRepository.getByIsbnBook(isbn));
            bookUser.setGenreUsers(genreUserRepository.getByIsbnBook(isbn));
            return Optional.of(bookUser);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
