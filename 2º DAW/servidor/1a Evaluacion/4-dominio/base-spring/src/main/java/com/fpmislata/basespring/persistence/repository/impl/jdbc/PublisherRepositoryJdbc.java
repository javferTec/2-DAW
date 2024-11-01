package com.fpmislata.basespring.persistence.repository.impl.jdbc;

import com.fpmislata.basespring.domain.model.Publisher;
import com.fpmislata.basespring.domain.repository.PublisherRepository;
import com.fpmislata.basespring.persistence.repository.impl.jdbc.common.DynamicSQLBuilder;
import com.fpmislata.basespring.persistence.repository.impl.jdbc.mapper.PublisherRowMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Getter
@Repository
@RequiredArgsConstructor
public class PublisherRepositoryJdbc implements PublisherRepository {

    private final DynamicSQLBuilder<Publisher> dynamicSQLBuilder;

    private static final String TABLE_NAME = "publishers";
    private static final String ID_COLUMN = "id";

    @Autowired
    public PublisherRepositoryJdbc(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.dynamicSQLBuilder = new DynamicSQLBuilder<>(
                jdbcTemplate,
                namedParameterJdbcTemplate,
                TABLE_NAME,
                List.of(ID_COLUMN),
                new PublisherRowMapper()
        );
    }

    @Override
    public Optional<Publisher> findById(Long id) {
        return dynamicSQLBuilder.findById(ID_COLUMN, id);
    }
}