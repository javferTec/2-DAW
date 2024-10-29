package com.fpmislata.basespring.domain.common.helper;

import com.fpmislata.basespring.common.exception.ResourceNotFoundException;
import com.fpmislata.basespring.persistence.common.GenericRepository;

import java.util.List;

public class BaseServiceHelper<Model, Repository extends GenericRepository<Model>> {

    private final Repository repository;

    public BaseServiceHelper(Repository repository) {
        this.repository = repository;
    }

    public List<Model> getAll() {
        return repository.getAll();
    }

    public List<Model> getAll(int page, int size) {
        return repository.getAll(page, size);
    }

    public int count() {
        return repository.count();
    }

    public Model findByIsbn(String isbn) {
        return repository.findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book with ISBN " + isbn + " not found"));
    }
}