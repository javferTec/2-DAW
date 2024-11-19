package com.fpmislata.basespring.persistence.dao.db;

import com.fpmislata.basespring.domain.model.Author;

import java.util.List;

public interface AuthorDaoDb extends GenericDaoDb<Author> {

    List<Author> getByIsbnBook(String isbn);
    List<Author> getByIdBook(long idBook);
    List<Author> findAllById(Long[] ids);
}