package com.fpmislata.basespring.persistence.dao.db.jdbc;

import com.fpmislata.basespring.common.annotation.persistence.Dao;
import com.fpmislata.basespring.domain.model.Publisher;
import com.fpmislata.basespring.persistence.dao.db.PublisherDaoDb;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Dao
public class PublisherDaoJdbc extends BaseDaoJdbc<Publisher> implements PublisherDaoDb {

    public PublisherDaoJdbc(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Optional<Publisher> getById(long id) {
        return super.getById(id);
    }

    @Override
    public List<Publisher> getAll() {
        return super.getAll();
    }

    @Override
    public List<Publisher> getAll(int page, int size) {
        return super.getAll(page, size);
    }

    @Override
    public long insert(Publisher publisher) {
        return super.insert(publisher);
    }

    @Override
    public void update(Publisher publisher) {
        super.update(publisher);
    }

    @Override
    public void delete(long id) {
        super.delete(id);
    }

    @Override
    public int count() {
        return super.count();
    }
}