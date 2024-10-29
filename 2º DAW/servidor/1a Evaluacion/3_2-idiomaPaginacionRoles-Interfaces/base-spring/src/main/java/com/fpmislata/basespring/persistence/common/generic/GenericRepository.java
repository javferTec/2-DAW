package com.fpmislata.basespring.persistence.common.generic;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface GenericRepository<Model> {
    List<Model> getAll();
    List<Model> getAll(int page, int size);
    int count();
    Optional<Model> findByIsbn(String isbn);
}