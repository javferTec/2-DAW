package com.fpmislata.basespring.persistence.dao.db;

import com.fpmislata.basespring.domain.model.Genre;

import java.util.List;

public interface GenreDaoDb extends GenericDaoDb<Genre> {
    List<Genre> getByIdBook(long idBook);

    List<Genre> getAllById(Long[] ids);
}