package com.fpmislata.basespring.controller;

import com.fpmislata.basespring.controller.webMapper.book.BookMapper;
import com.fpmislata.basespring.controller.webModel.PaginatedResponse;
import com.fpmislata.basespring.controller.webModel.book.BookCollection;
import com.fpmislata.basespring.controller.webModel.book.BookDetail;
import com.fpmislata.basespring.domain.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(BookController.URL)
public class BookController {
    //@Value("${app.base.url}")
    private String baseUrl = "http://localhost:8080";

    //@Value("${app.pageSize.default}")
    private String defaultPageSize = "10";

    public static final String URL = "/api/books";
    private final BookService bookService;

    @RequestMapping
    public ResponseEntity<PaginatedResponse<BookCollection>> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) Integer size) {

        int pageSize = (size != null) ? size : Integer.parseInt(defaultPageSize);
        List<BookCollection> books = bookService
                .findAll(page - 1, pageSize)
                .stream()
                .map(BookMapper.INSTANCE::toBookCollection)
                .toList();

        int total = bookService.count();

        PaginatedResponse<BookCollection> response = new PaginatedResponse<>(books, total, page, pageSize, baseUrl + URL);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping("/{isbn}")
    public ResponseEntity<BookDetail> findByIsbn(@PathVariable("isbn") String isbn) {
        BookDetail bookDetail = BookMapper.INSTANCE.toBookDetail(bookService.findByIsbn(isbn));
        return new ResponseEntity<>(bookDetail, HttpStatus.OK);
    }
}
