package com.fpmislata.basespring.roles.user.controller;

import com.fpmislata.basespring.roles.user.controller.mapper.book.BookUserMapper;
import com.fpmislata.basespring.common.layer.controller.pagination.PaginatedResponse;
import com.fpmislata.basespring.roles.user.controller.model.book.BookUserCollection;
import com.fpmislata.basespring.roles.user.controller.model.book.BookUserDetail;
import com.fpmislata.basespring.roles.user.domain.service.BookUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(BookUserController.URL)
public class BookUserController {
    @Value("${app.base.url}")
    private String baseUrl;

    @Value("${app.pageSize.default}")
    private String defaultPageSize;

    public static final String URL = "/api/books";
    private final BookUserService bookUserService;

    @RequestMapping
    public ResponseEntity<PaginatedResponse<BookUserCollection>> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) Integer size) {

        int pageSize = (size != null) ? size : Integer.parseInt(defaultPageSize);
        List<BookUserCollection> books = bookUserService
                .findAll(page - 1, pageSize)
                .stream()
                .map(BookUserMapper.INSTANCE::toBookCollection)
                .toList();

        int total = bookUserService.count();

        PaginatedResponse<BookUserCollection> response = new PaginatedResponse<>(books, total, page, pageSize, baseUrl + URL);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping("/{isbn}")
    public ResponseEntity<BookUserDetail> findByIsbn(@PathVariable("isbn") String isbn) {
        BookUserDetail bookUserDetail = BookUserMapper.INSTANCE.toBookDetail(bookUserService.findByIsbn(isbn));
        return new ResponseEntity<>(bookUserDetail, HttpStatus.OK);
    }
}
