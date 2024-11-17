package com.fpmislata.basespring.persistence.dao.cache;

import com.fpmislata.basespring.domain.model.Book;

import java.util.Optional;

public interface BookDaoCache extends GenericDaoCache<Book>{

    Optional<Book> findByIsbn(String isbn);
}
