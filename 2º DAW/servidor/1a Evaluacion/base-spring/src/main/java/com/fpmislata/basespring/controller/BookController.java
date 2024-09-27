package com.fpmislata.basespring.controller;

import com.fpmislata.basespring.domain.model.Book;
import com.fpmislata.basespring.domain.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(BookController.URL)
@RequiredArgsConstructor
public class BookController {
    public static final String URL = "/books";
    private final BookService bookService;

    @RequestMapping
    public List<Book> findAll() {
        return bookService.findAll();
    }

    @RequestMapping("/{isbn}")
    public Book findByIsbn(@PathVariable("isbn") String isbn) {
        return bookService.findByIsbn(isbn);
    }
}
