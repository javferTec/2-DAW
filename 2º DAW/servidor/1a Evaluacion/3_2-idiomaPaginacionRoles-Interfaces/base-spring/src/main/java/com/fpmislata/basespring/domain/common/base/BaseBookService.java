package com.fpmislata.basespring.domain.common.base;

import com.fpmislata.basespring.common.exception.ResourceNotFoundException;
import com.fpmislata.basespring.persistence.common.base.BookRepository;

import java.util.List;

public abstract class BaseBookService<T> {

    protected abstract BookRepository<T> getRepository();

    public List<T> findAll() {
        return getRepository().findAll();
    }

    public List<T> findAll(int page, int size) {
        return getRepository().findAll(page, size);
    }

    public int count() {
        return getRepository().count();
    }

    public T findByIsbn(String isbn) {
        return getRepository().findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book with ISBN " + isbn + " not found"));
    }
}