package com.fpmislata.basespring.domain.user.service.impl;

import com.fpmislata.basespring.common.exception.ResourceNotFoundException;
import com.fpmislata.basespring.domain.user.model.BookUser;
import com.fpmislata.basespring.domain.user.service.BookUserService;
import com.fpmislata.basespring.persistence.user.repository.BookUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookUserServiceImpl implements BookUserService {

    private final BookUserRepository bookUserRepository;

    @Override
    public List<BookUser> findAll() {
        return bookUserRepository.findAll();
    }

    @Override
    public List<BookUser> findAll(int page, int size) {
        return bookUserRepository.findAll(page, size);
    }

    @Override
    public int count() {
        return bookUserRepository.count();
    }

    public BookUser findByIsbn(String isbn) {
        return bookUserRepository.findByIsbn(isbn).orElseThrow(() -> new ResourceNotFoundException("Book with ISBN " + isbn + " not found"));
    }
}
