package com.fpmislata.basespring.persistence.repository.impl.jdbc;

import com.fpmislata.basespring.domain.model.Publisher;
import com.fpmislata.basespring.domain.repository.PublisherRepository;
import com.fpmislata.basespring.persistence.repository.impl.jdbc.common.JdbcOperations;
import com.fpmislata.basespring.persistence.repository.impl.jdbc.common.SqlBuilder;
import com.fpmislata.basespring.persistence.repository.impl.jdbc.mapper.PublisherRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PublisherRepositoryJdbc implements PublisherRepository {

    private final JdbcOperations jdbcOperations;
    private final SqlBuilder sqlBuilder;
    private final PublisherRowMapper publisherRowMapper;

    public static final String TABLE_NAME = "publishers";
    public static final String ID_COLUMN = "id";

    @Override
    public Optional<Publisher> findById(Long id) {
        String sql = sqlBuilder.findByColumn(TABLE_NAME, ID_COLUMN);
        return jdbcOperations.findById(sql, id, publisherRowMapper);
    }
}