package com.fpmislata.basespring.domain.useCase.book.roles.admin.impl;

import com.fpmislata.basespring.common.annotation.DomainTransactional;
import com.fpmislata.basespring.common.annotation.DomainUseCase;
import com.fpmislata.basespring.domain.model.Book;
import com.fpmislata.basespring.domain.service.BookService;
import com.fpmislata.basespring.domain.useCase.book.roles.admin.BookGetAllAdminUseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DomainUseCase
@DomainTransactional
@RequiredArgsConstructor
public class BookGetAllAdminUseCaseImpl implements BookGetAllAdminUseCase {

    private final BookService bookService;

    @Override
    public List<Book> execute(int page, int pageSize) {
        return bookService.getAll();
    }
}