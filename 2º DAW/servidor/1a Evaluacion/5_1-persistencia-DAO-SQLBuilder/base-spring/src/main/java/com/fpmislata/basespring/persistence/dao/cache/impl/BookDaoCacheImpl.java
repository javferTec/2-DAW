package com.fpmislata.basespring.persistence.dao.cache.impl;

import com.fpmislata.basespring.common.annotation.persistence.Dao;
import com.fpmislata.basespring.domain.model.Book;
import com.fpmislata.basespring.persistence.dao.cache.BookDaoCache;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Dao
public class BookDaoCacheImpl implements BookDaoCache {

    private static final long TTL = 600_000L; // 10 minutos en milisegundos
    private final Map<String, Book> cache = new ConcurrentHashMap<>();
    private final Map<String, Long> expiration = new ConcurrentHashMap<>();

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        Long expirationTime = expiration.get(isbn);
        if (expirationTime != null && expirationTime >= System.currentTimeMillis()) {
            System.out.println("Retrieved from cache: " + isbn);
            return Optional.ofNullable(cache.get(isbn));
        }
        cache.remove(isbn);
        expiration.remove(isbn);
        return Optional.empty();
    }

    @Override
    public void save(Book book) {
        cache.put(book.getIsbn(), book);
        expiration.put(book.getIsbn(), System.currentTimeMillis() + TTL);
    }

    @Override
    public void clearCache() {
        cache.clear();
        expiration.clear();
    }
}
