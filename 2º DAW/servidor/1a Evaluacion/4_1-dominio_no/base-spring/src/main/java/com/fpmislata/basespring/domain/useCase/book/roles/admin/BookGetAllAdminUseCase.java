package com.fpmislata.basespring.domain.useCase.book.roles.admin;

import com.fpmislata.basespring.domain.model.Book;

import java.util.List;

public interface BookGetAllAdminUseCase {
    List<Book> execute(int page, int pageSize);
}