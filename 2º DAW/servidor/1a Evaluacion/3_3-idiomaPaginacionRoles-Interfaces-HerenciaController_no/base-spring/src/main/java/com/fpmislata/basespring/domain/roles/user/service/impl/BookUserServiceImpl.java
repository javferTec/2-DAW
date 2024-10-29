package com.fpmislata.basespring.domain.roles.user.service.impl;

import com.fpmislata.basespring.domain.common.helper.BaseServiceHelper;
import com.fpmislata.basespring.domain.roles.user.model.BookUser;
import com.fpmislata.basespring.domain.roles.user.service.BookUserService;
import com.fpmislata.basespring.persistence.roles.user.repository.impl.jdbc.BookUserRepositoryImplJdbc;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookUserServiceImpl implements BookUserService {

    @Getter
    private final BookUserRepositoryImplJdbc repository;
    private final BaseServiceHelper<BookUser, BookUserRepositoryImplJdbc> helper;

    @Autowired
    public BookUserServiceImpl(BookUserRepositoryImplJdbc repository) {
        this.repository = repository;
        this.helper = new BaseServiceHelper<>(repository);
    }

    @Override
    public List<BookUser> getAll() {
        return helper.getAll();
    }

    @Override
    public List<BookUser> getAll(int page, int size) {
        return helper.getAll(page, size);
    }

    @Override
    public int count() {
        return helper.count();
    }

    @Override
    public BookUser findByIsbn(String isbn) {
        return helper.findByIsbn(isbn);
    }
}
