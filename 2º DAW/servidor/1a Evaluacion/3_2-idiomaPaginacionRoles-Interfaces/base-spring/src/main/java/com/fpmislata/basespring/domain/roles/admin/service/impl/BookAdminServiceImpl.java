package com.fpmislata.basespring.domain.roles.admin.service.impl;

import com.fpmislata.basespring.domain.roles.admin.model.BookAdmin;
import com.fpmislata.basespring.domain.roles.admin.service.BookAdminService;
import com.fpmislata.basespring.domain.common.helper.BaseServiceHelper;
import com.fpmislata.basespring.persistence.roles.admin.repository.impl.jdbc.BookAdminRepositoryImplJdbc;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookAdminServiceImpl implements BookAdminService {

    @Getter
    private final BookAdminRepositoryImplJdbc repository;
    private final BaseServiceHelper<BookAdmin, BookAdminRepositoryImplJdbc> helper;

    @Autowired
    public BookAdminServiceImpl(BookAdminRepositoryImplJdbc repository) {
        this.repository = repository;
        this.helper = new BaseServiceHelper<>(repository);
    }

    @Override
    public List<BookAdmin> getAll() {
        return helper.getAll();
    }

    @Override
    public List<BookAdmin> getAll(int page, int size) {
        return helper.getAll(page, size);
    }

    @Override
    public int count() {
        return helper.count();
    }

    @Override
    public BookAdmin findByIsbn(String isbn) {
        return helper.findByIsbn(isbn);
    }

}