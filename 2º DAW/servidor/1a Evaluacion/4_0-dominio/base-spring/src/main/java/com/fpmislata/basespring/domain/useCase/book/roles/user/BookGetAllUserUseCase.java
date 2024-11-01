package com.fpmislata.basespring.domain.useCase.book.roles.user;

import com.fpmislata.basespring.domain.useCase.book.roles.user.model.BookUser;

import java.util.List;

public interface BookGetAllUserUseCase {
    List<BookUser> execute(int page, int pageSize);
}