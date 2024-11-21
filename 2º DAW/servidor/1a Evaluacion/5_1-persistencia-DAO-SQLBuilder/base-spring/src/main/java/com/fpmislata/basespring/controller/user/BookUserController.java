package com.fpmislata.basespring.controller.user;

import com.fpmislata.basespring.controller.common.BaseController;
import com.fpmislata.basespring.controller.common.pagination.PaginatedResponse;
import com.fpmislata.basespring.controller.user.userMapper.book.BookUserMapper;
import com.fpmislata.basespring.controller.user.userModel.book.BookUserCollection;
import com.fpmislata.basespring.controller.user.userModel.book.BookUserDetail;
import com.fpmislata.basespring.domain.useCase.book.common.BookCountUseCase;
import com.fpmislata.basespring.domain.useCase.book.common.BookFindByIsbnUseCase;
import com.fpmislata.basespring.domain.useCase.book.common.BookGetAllUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(BookUserController.URL)
public class BookUserController extends BaseController {

    public static final String URL = "/api/books";

    private final BookGetAllUseCase bookGetAllUseCase;
    private final BookCountUseCase bookCountUseCase;
    private final BookFindByIsbnUseCase bookFindByIsbnUseCase;

    @GetMapping
    public ResponseEntity<PaginatedResponse<BookUserCollection>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) Integer size) {

        return super.getAll(
                page,
                size,
                offset -> bookGetAllUseCase.execute(offset, getPageSize(size)),
                book -> modelMapper.map(book, BookUserCollection.class),
                bookCountUseCase.execute(),
                URL
        );
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<BookUserDetail> findByIsbn(@PathVariable String isbn) {
        return super.findByIsbn(
                isbn,
                bookFindByIsbnUseCase::execute,
                BookUserMapper::toBookDetail
        );
    }

}

