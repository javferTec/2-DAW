package com.fpmislata.basespring.persistence.common.generic;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface GenericBookRepository<BookModel> {
    List<BookModel> getAll();
    List<BookModel> getAll(int page, int size);
    int count();
    Optional<BookModel> findByIsbn(String isbn);
}