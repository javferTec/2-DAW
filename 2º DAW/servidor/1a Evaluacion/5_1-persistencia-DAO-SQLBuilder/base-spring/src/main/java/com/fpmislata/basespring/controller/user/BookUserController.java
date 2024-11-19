package com.fpmislata.basespring.controller.user;

import com.fpmislata.basespring.controller.common.pagination.PaginatedResponse;
import com.fpmislata.basespring.controller.common.pagination.ResponseBuilder;
import com.fpmislata.basespring.controller.user.userMapper.book.BookUserMapper;
import com.fpmislata.basespring.controller.user.userModel.book.BookUserCollection;
import com.fpmislata.basespring.controller.user.userModel.book.BookUserDetail;
import com.fpmislata.basespring.domain.useCase.book.common.BookCountUseCase;
import com.fpmislata.basespring.domain.useCase.book.common.BookFindByIsbnUseCase;
import com.fpmislata.basespring.domain.useCase.book.common.BookGetAllUseCase;
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
    private final ResponseBuilder responseBuilder;

    private final BookGetAllUseCase bookGetAllUseCase;
    private final BookCountUseCase bookCountUseCase;
    private final BookFindByIsbnUseCase bookFindByIsbnUseCase;

    @GetMapping
    public ResponseEntity<PaginatedResponse<BookUserCollection>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) Integer size) {

        int pageSize = responseBuilder.getPageSize(size);

        List<BookUserCollection> books = bookGetAllUseCase.execute(page - 1, pageSize)
                .stream()
                .map(BookUserMapper.INSTANCE::toBookCollection)
                .toList();

        return responseBuilder.createPaginatedResponse(books, bookCountUseCase.execute(), page, pageSize, URL);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<BookUserDetail> findByIsbn(@PathVariable String isbn) {
        return new ResponseEntity<>(
                BookUserMapper.INSTANCE.toBookDetail(bookFindByIsbnUseCase.execute(isbn)),
                HttpStatus.OK
        );
    }

}

