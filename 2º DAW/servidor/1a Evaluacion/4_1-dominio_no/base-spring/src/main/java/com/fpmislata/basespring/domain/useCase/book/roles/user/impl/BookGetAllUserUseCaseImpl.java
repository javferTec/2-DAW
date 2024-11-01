package com.fpmislata.basespring.domain.useCase.book.roles.user.impl;

import com.fpmislata.basespring.common.annotation.DomainTransactional;
import com.fpmislata.basespring.common.annotation.DomainUseCase;
import com.fpmislata.basespring.domain.repository.BookRepository;
import com.fpmislata.basespring.domain.service.BookService;
import com.fpmislata.basespring.domain.useCase.book.roles.user.BookGetAllUserUseCase;
import com.fpmislata.basespring.domain.useCase.book.roles.user.mapper.BookUserMapper;
import com.fpmislata.basespring.domain.useCase.book.roles.user.model.BookUser;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DomainUseCase
@DomainTransactional
@RequiredArgsConstructor
public class BookGetAllUserUseCaseImpl implements BookGetAllUserUseCase {

    private final BookService bookService;
    private final BookRepository bookRepository;

    @Override
    public List<BookUser> execute(int page, int pageSize) {
        return bookRepository
                .getAll()
                .stream()
                .map(BookUserMapper.INSTANCE::toBookUser)
                .toList();
    }
}