package com.fpmislata.basespring.domain.user.service.impl;

import com.fpmislata.basespring.domain.common.base.BaseBookService;
import com.fpmislata.basespring.domain.user.model.BookUser;
import com.fpmislata.basespring.domain.user.service.BookUserService;
import com.fpmislata.basespring.persistence.common.base.BookRepository;
import com.fpmislata.basespring.persistence.user.repository.BookUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BookUserServiceImpl extends BaseBookService<BookUser> implements BookUserService {

    private final BookUserRepository bookUserRepository;

    @Override
    protected BookRepository<BookUser> getRepository() {
        return bookUserRepository;
    }
}
