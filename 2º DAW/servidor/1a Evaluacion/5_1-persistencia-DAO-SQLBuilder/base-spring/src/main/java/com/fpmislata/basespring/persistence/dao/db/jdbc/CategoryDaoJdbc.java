package com.fpmislata.basespring.persistence.dao.db.jdbc;

import com.fpmislata.basespring.common.annotation.persistence.Dao;
import com.fpmislata.basespring.domain.model.Category;
import com.fpmislata.basespring.persistence.dao.db.CategoryDaoDb;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Dao
public class CategoryDaoJdbc extends BaseDaoJdbc<Category> implements CategoryDaoDb {

    public CategoryDaoJdbc(DataSource dataSource) {
        super(Category.class, dataSource);
    }

    @Override
    public Optional<Category> getById(long id) {
        return super.getById(id);
    }

    @Override
    public List<Category> getAll() {
        return super.getAll();
    }

    @Override
    public List<Category> getAll(int page, int size) {
        return super.getAll(page, size);
    }

    @Override
    public long insert(Category category) {
        return super.insert(category);
    }

    @Override
    public void update(Category category) {
        super.update(category);
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
