package com.fpmislata.basespring.persistence.dao.db.jdbc;

import com.fpmislata.basespring.common.annotation.persistence.Dao;
import com.fpmislata.basespring.domain.model.Publisher;
import com.fpmislata.basespring.persistence.dao.db.PublisherDaoDb;
import com.fpmislata.basespring.persistence.dao.db.jdbc.mapper.generic.GenericRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

@Dao
public class PublisherDaoJdbc implements PublisherDaoDb {

    private final JdbcTemplate jdbcTemplate;
    private final GenericRowMapper<Publisher> publisherRowMapper;

    public PublisherDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.publisherRowMapper = new GenericRowMapper<>(Publisher.class, jdbcTemplate);
    }

    @Override
    public Optional<Publisher> findById(long id) {
        String sql = """
                SELECT * FROM publishers
                WHERE id = ?
                """;

        //return Optional.ofNullable(jdbcTemplate.queryForObject(sql, publisherRowMapper, id));

        try
        {
            return Optional.of(jdbcTemplate.queryForObject(sql, publisherRowMapper, id));
        }
        catch (Exception e)
        {
            return Optional.empty();
        }
    }


    @Override
    public List<Publisher> getAll() {
        return List.of();
    }

    @Override
    public List<Publisher> getAll(int page, int size) {
        return List.of();
    }

    @Override
    public long insert(Publisher publisher) {
        return 0;
    }

    @Override
    public void update(Publisher publisher) {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public int count() {
        return 0;
    }
}