package com.fpmislata.basespring.persistence.repository.impl;

import com.fpmislata.basespring.domain.model.Genre;
import com.fpmislata.basespring.domain.repository.GenreRepository;
import com.fpmislata.basespring.persistence.dao.db.GenreDaoDb;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryJdbc implements GenreRepository {

    private final GenreDaoDb genreDao;

    @Override
    public List<Genre> getByIsbnBook(String isbn) {
        return genreDao.getByIsbnBook(isbn);
    }

    @Override
    public List<Genre> getByIdBook(long idBook) {
        return genreDao.getByIdBook(idBook);
    }

    @Override
    public List<Genre> findAllById(Long[] ids) {
        return genreDao.findAllById(ids);
    }
}