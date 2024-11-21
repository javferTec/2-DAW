package com.fpmislata.basespring.persistence.dao.db.jdbc;

import com.fpmislata.basespring.common.annotation.persistence.Dao;
import com.fpmislata.basespring.domain.model.Author;
import com.fpmislata.basespring.persistence.dao.db.AuthorDaoDb;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Dao
public class AuthorDaoJdbc extends BaseDaoJdbc<Author> implements AuthorDaoDb {

    public AuthorDaoJdbc(DataSource dataSource) {
        super(Author.class, dataSource);
    }

    @Override
    public List<Author> getByIsbnBook(String isbn) {
        String sql = super.getSelectSql() + """
                     JOIN books_authors ON authors.id = books_authors.author_id
                     JOIN books ON books_authors.book_id = books.id
                     AND books.isbn = ?
                """;

        Map<String, Object> params = Map.of("isbn", isbn);
        return super.customSqlQueryForList(sql, params);
    }

    @Override
    public List<Author> getByIdBook(long idBook) {
        String sql = super.getSelectSql() + """
                     JOIN books_authors ON authors.id = books_authors.author_id
                     AND books_authors.book_id = ?
                """;

        Map<String, Object> params = Map.of("idBook", idBook);
        return super.customSqlQueryForList(sql, params);
    }

    @Override
    public List<Author> getAllByIds(Long[] ids) {
        return super.getAllByIds(ids);
    }


    @Override
    public List<Author> getAll() {
        return super.getAll();
    }

    @Override
    public List<Author> getAll(int page, int size) {
        return super.getAll(page, size);
    }

    //@Override
    public Optional<Author> getById(long id) {
        return super.getById(id);
    }

    @Override
    public long insert(Author author) {
        return super.insert(author);
    }

    @Override
    public void update(Author author) {
        super.update(author);
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