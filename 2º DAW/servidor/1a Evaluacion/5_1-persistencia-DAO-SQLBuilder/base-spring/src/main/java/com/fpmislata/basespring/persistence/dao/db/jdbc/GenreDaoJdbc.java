package com.fpmislata.basespring.persistence.dao.db.jdbc;

import com.fpmislata.basespring.common.annotation.persistence.Dao;
import com.fpmislata.basespring.domain.model.Genre;
import com.fpmislata.basespring.persistence.dao.db.GenreDaoDb;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Dao
public class GenreDaoJdbc extends BaseDaoJdbc<Genre> implements GenreDaoDb {

    @Override
    public List<Genre> getByIsbnBook(String isbn) {
        String sql = super.getSelectSql() + """
                JOIN books_genres ON genres.id = books_genres.genre_id
                JOIN books ON books_genres.book_id = books.id
                AND books.isbn = ?
           """;
        Map<String, String> params = Map.of("isbn", isbn);
        return super.customSqlQueryForList(sql, params);
    }

    @Override
    public List<Genre> getByIdBook(long idBook) {
        String sql = super.getSelectSql() + """
                JOIN books_genres ON genres.id = books_genres.genre_id
                AND books_genres.book_id = ?
           """;
        Map<String, Long> params = Map.of("idBook", idBook);
        return super.customSqlQueryForList(sql, params);
    }

    @Override
    public List<Genre> findAllById(Long[] ids) {
        return super.getAllByIds(ids);
    }

    @Override
    public List<Genre> getAll() {
        return super.getAll();
    }

    @Override
    public List<Genre> getAll(int page, int size) {
        return super.getAll(page, size);
    }

    @Override
    public Optional<Genre> getById(long id) {
        return super.getById(id);
    }

    @Override
    public long insert(Genre genre) {
        return super.insert(genre);
    }

    @Override
    public void update(Genre genre) {
        super.update(genre);
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
