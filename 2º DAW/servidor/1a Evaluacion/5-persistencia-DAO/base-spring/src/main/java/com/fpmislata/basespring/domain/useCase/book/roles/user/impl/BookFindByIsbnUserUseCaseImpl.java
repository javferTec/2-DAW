package com.fpmislata.basespring.domain.useCase.book.roles.user.impl;

import com.fpmislata.basespring.common.annotation.domain.DomainUseCase;
import com.fpmislata.basespring.common.exception.ResourceNotFoundException;
import com.fpmislata.basespring.domain.service.BookService;
import com.fpmislata.basespring.domain.useCase.book.roles.user.BookFindByIsbnUserUseCase;
import com.fpmislata.basespring.domain.useCase.book.roles.user.mapper.BookUserMapper;
import com.fpmislata.basespring.domain.useCase.book.roles.user.model.BookUser;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@RequiredArgsConstructor
public class BookFindByIsbnUserUseCaseImpl implements BookFindByIsbnUserUseCase {

    private final BookService bookService;

    @Override
    public BookUser execute(String isbn) {
        return BookUserMapper.INSTANCE.toBookUser(
                bookService
                        .findByIsbn(isbn)
                        .orElseThrow(() -> new ResourceNotFoundException("Book isbn " + isbn + " not found"))
        );
    }
}