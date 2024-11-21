package com.fpmislata.basespring.domain.useCase.book.common.impl;

import com.fpmislata.basespring.common.annotation.domain.DomainUseCase;
import com.fpmislata.basespring.common.exception.ResourceNotFoundException;
import com.fpmislata.basespring.domain.model.Book;
import com.fpmislata.basespring.domain.service.BookService;
import com.fpmislata.basespring.domain.useCase.book.common.BookFindByIsbnUseCase;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@RequiredArgsConstructor
public class BookFindByIsbnUseCaseImpl implements BookFindByIsbnUseCase {

    private final BookService bookService;

    @Override
    public Book execute(String isbn) {
        return bookService
                .findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book isbn " + isbn + " not found"));
    }
}