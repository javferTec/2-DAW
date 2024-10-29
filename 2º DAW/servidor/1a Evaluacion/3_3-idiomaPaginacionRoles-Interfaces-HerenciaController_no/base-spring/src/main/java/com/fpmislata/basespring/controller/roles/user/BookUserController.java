package com.fpmislata.basespring.controller.roles.user;

import com.fpmislata.basespring.controller.common.helper.BaseControllerHelper;
import com.fpmislata.basespring.controller.common.pagination.PaginatedResponse;
import com.fpmislata.basespring.controller.roles.user.userMapper.book.BookUserMapper;
import com.fpmislata.basespring.controller.roles.user.userModel.book.BookUserCollection;
import com.fpmislata.basespring.controller.roles.user.userModel.book.BookUserDetail;
import com.fpmislata.basespring.domain.roles.user.service.BookUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(BookUserController.URL)
public class BookUserController {

    public static final String URL = "/api/books";
    private final BookUserService bookUserService;
    private final BaseControllerHelper baseControllerHelper;

    @GetMapping
    public ResponseEntity<PaginatedResponse<BookUserCollection>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) Integer size) {

        int pageSize = baseControllerHelper.getPageSize(size);

        List<BookUserCollection> books = bookUserService.getAll(page - 1, pageSize)
                .stream()
                .map(BookUserMapper.INSTANCE::toBookCollection)
                .toList();

        return baseControllerHelper.createPaginatedResponse(books, bookUserService.count(), page, pageSize, URL);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<BookUserDetail> findByIsbn(@PathVariable String isbn) {
        return new ResponseEntity<>(
                BookUserMapper.INSTANCE.toBookDetail(bookUserService.findByIsbn(isbn)),
                HttpStatus.OK
        );
    }

}

