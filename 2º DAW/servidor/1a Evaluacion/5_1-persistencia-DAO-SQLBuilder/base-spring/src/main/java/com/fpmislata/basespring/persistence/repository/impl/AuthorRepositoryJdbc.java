package com.fpmislata.basespring.persistence.repository.impl;

import com.fpmislata.basespring.domain.model.Author;
import com.fpmislata.basespring.domain.repository.AuthorRepository;
import com.fpmislata.basespring.persistence.dao.db.AuthorDaoDb;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryJdbc implements AuthorRepository {

    private final AuthorDaoDb authorDao;

    @Override
    public List<Author> getByIsbnBook(String isbn) {
        return authorDao.getByIsbnBook(isbn);
    }

    @Override
    public List<Author> getByIdBook(long idBook) {
        return authorDao.getByIdBook(idBook);
    }

    @Override
    public List<Author> findAllById(Long[] ids) {
        return authorDao.findAllById(ids);
    }
}