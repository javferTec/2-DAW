package com.fpmislata.basespring.domain.service.impl;

import com.fpmislata.basespring.domain.model.Book;
import com.fpmislata.basespring.domain.service.BookService;
import com.fpmislata.basespring.persistence.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public List<Book> findAll() {
        System.out.println("findAll");
        return bookRepository.findAll();
    }

    public Book findByIsbn(String isbn) {
        System.out.println("findByIsbn");
        return bookRepository.findByIsbn(isbn).orElseThrow(() -> new RuntimeException("Book not found"));
    }
}
