package com.fpmislata.basespring.domain.useCase.book.common;

import com.fpmislata.basespring.domain.model.Book;

import java.util.List;

public interface BookGetAllUseCase {
    List<Book> execute(int page, int pageSize);
}