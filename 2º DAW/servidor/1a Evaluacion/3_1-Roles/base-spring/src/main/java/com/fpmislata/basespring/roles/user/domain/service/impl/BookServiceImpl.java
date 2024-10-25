package com.fpmislata.basespring.roles.user.domain.service.impl;

import com.fpmislata.basespring.common.layers.domain.exception.ResourceNotFoundException;
import com.fpmislata.basespring.roles.user.domain.model.Book;
import com.fpmislata.basespring.roles.user.domain.service.BookService;
import com.fpmislata.basespring.roles.user.domain.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public List<Book> findAll(int page, int size) {
        return bookRepository.findAll(page,size);
    }

    @Override
    public int count() {
        return bookRepository.count();
    }

    public Book findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn).orElseThrow(() -> new ResourceNotFoundException("Book with ISBN " + isbn + " not found"));
    }
}
