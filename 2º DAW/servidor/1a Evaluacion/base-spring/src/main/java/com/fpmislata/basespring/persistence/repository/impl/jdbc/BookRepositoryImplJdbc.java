package com.fpmislata.basespring.persistence.repository.impl.jdbc;

import com.fpmislata.basespring.domain.model.Book;
import com.fpmislata.basespring.persistence.repository.BookRepository;
import com.fpmislata.basespring.persistence.repository.impl.jdbc.mapper.BookRowMapper;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImplJdbc implements BookRepository {

    private final JdbcTemplate jdbcTemplate;


    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM book";
        return jdbcTemplate.query(sql, new BookRowMapper());
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return Optional.empty();
    }
}
