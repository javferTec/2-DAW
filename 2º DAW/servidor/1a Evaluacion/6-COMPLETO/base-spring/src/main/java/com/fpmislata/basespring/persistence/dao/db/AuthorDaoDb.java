package com.fpmislata.basespring.persistence.dao.db;

import com.fpmislata.basespring.domain.model.Author;

import java.util.List;

public interface AuthorDaoDb extends GenericDaoDb<Author> {
    List<Author> getByIdBook(long idBook);

    List<Author> getAllByIds(Long[] ids);
}