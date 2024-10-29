package com.fpmislata.basespring.persistence.common;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class JdbcHelper {
    private final JdbcTemplate jdbcTemplate;

    public <T> List<T> getAll(String sql, Object[] args, RowMapper<T> rowMapper) {
        return jdbcTemplate.query(sql, rowMapper, args);
    }

    public int count(String sql) {
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return (count != null) ? count : 0;
    }

    public <T> Optional<T> findByColumn(String sql, String column, RowMapper<T> rowMapper) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, column));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

}
