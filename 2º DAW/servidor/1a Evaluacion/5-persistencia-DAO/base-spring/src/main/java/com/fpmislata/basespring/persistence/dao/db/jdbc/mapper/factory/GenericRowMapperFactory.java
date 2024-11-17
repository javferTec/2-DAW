package com.fpmislata.basespring.persistence.dao.db.jdbc.mapper.factory;

import com.fpmislata.basespring.persistence.dao.db.jdbc.mapper.generic.GenericRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

@RequiredArgsConstructor
public class GenericRowMapperFactory {
    private final JdbcTemplate jdbcTemplate;

    public <T> GenericRowMapper<T> createRowMapper(Class<T> type) {
        return new GenericRowMapper<>(type, jdbcTemplate);
    }
}
