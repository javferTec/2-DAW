package com.fpmislata.basespring.persistence.common.base;

import java.util.List;
import java.util.Optional;

public interface BookRepository<T> {
    List<T> findAll();
    List<T> findAll(int page, int size);
    int count();
    Optional<T> findByIsbn(String isbn);
}