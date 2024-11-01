package com.fpmislata.basespring.domain.useCase.book.common.impl;

import com.fpmislata.basespring.common.annotation.DomainTransactional;
import com.fpmislata.basespring.common.annotation.DomainUseCase;
import com.fpmislata.basespring.domain.service.BookService;
import com.fpmislata.basespring.domain.useCase.book.common.BookCountUseCase;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@DomainTransactional
@RequiredArgsConstructor
public class BookCountUseCaseImpl implements BookCountUseCase {

    private final BookService bookService;

    @Override
    public int execute() {
        return bookService.count();
    }
}