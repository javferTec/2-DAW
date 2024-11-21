package com.fpmislata.basespring.persistence.dao.db.jdbc;

import com.fpmislata.basespring.common.annotation.persistence.Dao;
import com.fpmislata.basespring.domain.model.Book;
import com.fpmislata.basespring.persistence.dao.db.BookDaoDb;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Dao
public class BookDaoJdbc extends BaseDaoJdbc<Book> implements BookDaoDb {

    public BookDaoJdbc(DataSource dataSource) {
        super(Book.class, dataSource);
    }

    @Override
    public List<Book> getAll() {
        return super.getAll();
    }

    @Override
    public List<Book> getAll(int page, int size) {
        return super.getAll(page, size);
    }

    @Override
    public int count() {
        return super.count();
    }

    @Override
    public Optional<Book> getByIsbn(String isbn) {
        Map<String, Object> criteria = Map.of("isbn", isbn);
        return super.getByCriteria(criteria).stream().findFirst();
    }

    @Override
    public Optional<Book> getById(long id) {
        return super.getById(id);
    }

    @Override
    public void update(Book book) {
        super.update(book);
    }

    @Override
    public void delete(long id) {
        super.delete(id);
    }

    @Override
    public long insert(Book book) {
        return super.insert(book);
    }
}
