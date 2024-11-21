package com.fpmislata.basespring.persistence.dao.db;

import com.fpmislata.basespring.domain.model.Book;

import java.util.Optional;

public interface BookDaoDb extends GenericDaoDb<Book> {
    Optional<Book> getByIsbn(String isbn);
}
