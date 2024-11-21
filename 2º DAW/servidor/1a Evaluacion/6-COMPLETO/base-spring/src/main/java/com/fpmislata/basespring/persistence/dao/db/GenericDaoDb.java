package com.fpmislata.basespring.persistence.dao.db;

import java.util.List;
import java.util.Optional;

public interface GenericDaoDb<T> {

    List<T> getAll();

    List<T> getAll(int page, int size);

    Optional<T> getById(long id);

    long insert(T t);

    void update(T t);

    void delete(long id);

    int count();
}
