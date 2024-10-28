package com.fpmislata.basespring.domain.admin.service.impl;

import com.fpmislata.basespring.domain.admin.model.BookAdmin;
import com.fpmislata.basespring.domain.admin.service.BookAdminService;
import com.fpmislata.basespring.domain.common.base.BaseBookService;
import com.fpmislata.basespring.persistence.admin.repository.BookAdminRepository;
import com.fpmislata.basespring.persistence.common.base.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookAdminServiceImpl extends BaseBookService<BookAdmin> implements BookAdminService {

    private final BookAdminRepository bookAdminRepository;

    @Override
    protected BookRepository<BookAdmin> getRepository() {
        return bookAdminRepository;
    }
}