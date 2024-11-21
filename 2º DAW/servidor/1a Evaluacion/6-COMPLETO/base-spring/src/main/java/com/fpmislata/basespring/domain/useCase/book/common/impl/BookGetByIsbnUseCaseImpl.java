package com.fpmislata.basespring.domain.useCase.book.common.impl;

import com.fpmislata.basespring.common.annotation.domain.DomainUseCase;
import com.fpmislata.basespring.common.exception.ResourceNotFoundException;
import com.fpmislata.basespring.domain.model.Book;
import com.fpmislata.basespring.domain.service.BookService;
import com.fpmislata.basespring.domain.useCase.book.common.BookGetByIsbnUseCase;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@RequiredArgsConstructor
public class BookGetByIsbnUseCaseImpl implements BookGetByIsbnUseCase {

    private final BookService bookService;

    @Override
    public Book execute(String isbn) {
        return bookService
                .getByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book isbn " + isbn + " not found"));
    }
}