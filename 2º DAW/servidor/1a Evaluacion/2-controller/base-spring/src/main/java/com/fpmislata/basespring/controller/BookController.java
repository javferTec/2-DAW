package com.fpmislata.basespring.controller;

import com.fpmislata.basespring.controller.webMapper.book.BookMapper;
import com.fpmislata.basespring.controller.webModel.book.BookCollection;
import com.fpmislata.basespring.controller.webModel.book.BookDetail;
import com.fpmislata.basespring.domain.model.Book;
import com.fpmislata.basespring.domain.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(BookController.URL)
@RequiredArgsConstructor
public class BookController {
    public static final String URL = "/api/books";
    private final BookService bookService;

    @RequestMapping
    public ResponseEntity<List<BookCollection>> findAll() {
        List<BookCollection> bookCollections = bookService.findAll()
                .stream()
                .map(BookMapper.INSTANCE::toBookCollection)
                .collect(Collectors.toList());
        return new ResponseEntity<>(bookCollections, HttpStatus.OK);
    }

    @RequestMapping("/{isbn}")
    public ResponseEntity<BookDetail> findByIsbn(@PathVariable("isbn") String isbn) {
        BookDetail bookDetail = BookMapper.INSTANCE.toBookDetail(bookService.findByIsbn(isbn));
        return new ResponseEntity<>(bookDetail, HttpStatus.OK);
    }
}
