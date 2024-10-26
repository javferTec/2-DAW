package com.fpmislata.basespring.roles.admin.domain.service.impl;

import com.fpmislata.basespring.common.exception.ResourceNotFoundException;
import com.fpmislata.basespring.roles.admin.domain.model.BookAdmin;
import com.fpmislata.basespring.roles.admin.domain.repository.BookAdminRepository;
import com.fpmislata.basespring.roles.admin.domain.service.BookAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookAdminServiceImpl implements BookAdminService {

    private final BookAdminRepository bookAdminRepository;

    @Override
    public List<BookAdmin> findAll() {
        return bookAdminRepository.findAll();
    }

    @Override
    public List<BookAdmin> findAll(int page, int size) {
        return bookAdminRepository.findAll(page, size);
    }

    @Override
    public int count() {
        return bookAdminRepository.count();
    }

    @Override
    public BookAdmin findByIsbn(String isbn) {
        return bookAdminRepository.findByIsbn(isbn).orElseThrow(() -> new ResourceNotFoundException("Book isbn " + isbn + " not found"));
    }
}