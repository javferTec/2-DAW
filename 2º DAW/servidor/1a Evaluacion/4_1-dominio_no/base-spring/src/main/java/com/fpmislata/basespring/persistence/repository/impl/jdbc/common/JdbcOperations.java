package com.fpmislata.basespring.persistence.repository.impl.jdbc.common;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component
public class JdbcOperations {
    private final JdbcTemplate jdbcTemplate;

    public <T> List<T> getAll(String sql, Object[] args, RowMapper<T> rowMapper) {
        return jdbcTemplate.query(sql, rowMapper, args);
    }

    public int count(String sql) {
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return (count != null) ? count : 0;
    }

    public <T> Optional<T> findByIsbn(String sql, String column, RowMapper<T> rowMapper) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, column));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public <T> Optional<T> findById(String sql, long id, RowMapper<T> rowMapper) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    // save -> insert and update
    public void save(String sql, Object[] args) {
        jdbcTemplate.update(sql, args);
    }

    public Optional<Integer> insertAndReturnId(String sql, Object[] args) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, Integer.class, args));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void delete(String sql, Object[] args) {
        jdbcTemplate.update(sql, args);
    }

}
