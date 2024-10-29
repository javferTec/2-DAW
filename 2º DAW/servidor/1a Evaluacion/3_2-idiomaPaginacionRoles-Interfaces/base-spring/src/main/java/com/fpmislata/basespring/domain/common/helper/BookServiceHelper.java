package com.fpmislata.basespring.domain.common.helper;

import com.fpmislata.basespring.common.exception.ResourceNotFoundException;
import com.fpmislata.basespring.persistence.common.generic.GenericBookRepository;

import java.util.List;

public class BookServiceHelper<BookModel, Repository extends GenericBookRepository<BookModel>> {

    private final Repository bookRepository;

    public BookServiceHelper(Repository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookModel> getAll() {
        return bookRepository.getAll();
    }

    public List<BookModel> getAll(int page, int size) {
        return bookRepository.getAll(page, size);
    }

    public int count() {
        return bookRepository.count();
    }

    public BookModel findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book with ISBN " + isbn + " not found"));
    }
}