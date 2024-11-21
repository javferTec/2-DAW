package com.fpmislata.basespring.domain.useCase.book.admin.impl;

import com.fpmislata.basespring.common.annotation.domain.DomainTransactional;
import com.fpmislata.basespring.common.annotation.domain.DomainUseCase;
import com.fpmislata.basespring.common.exception.ResourceNotFoundException;
import com.fpmislata.basespring.domain.service.BookService;
import com.fpmislata.basespring.domain.useCase.book.admin.BookDeleteUseCase;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@DomainTransactional
@RequiredArgsConstructor
public class BookDeleteUseCaseImpl implements BookDeleteUseCase {

    private final BookService bookService;

    @Override
    public void execute(long id) {
        bookService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book " + id + " not found"));
        bookService.delete(id);
    }
}
