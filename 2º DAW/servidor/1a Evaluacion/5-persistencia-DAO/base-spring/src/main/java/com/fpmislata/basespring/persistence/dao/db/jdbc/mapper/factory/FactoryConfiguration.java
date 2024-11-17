package com.fpmislata.basespring.persistence.dao.db.jdbc.mapper.factory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class FactoryConfiguration {

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public GenericRowMapperFactory genericRowMapperFactory(JdbcTemplate jdbcTemplate) {
        return new GenericRowMapperFactory(jdbcTemplate);
    }
}
