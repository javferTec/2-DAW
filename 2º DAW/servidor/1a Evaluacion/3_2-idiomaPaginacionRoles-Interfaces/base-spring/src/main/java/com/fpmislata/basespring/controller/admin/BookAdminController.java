package com.fpmislata.basespring.controller.admin;

import com.fpmislata.basespring.controller.admin.adminMapper.book.BookAdminMapper;
import com.fpmislata.basespring.controller.admin.adminModel.book.BookAdminCollection;
import com.fpmislata.basespring.controller.common.helper.BaseControllerHelper;
import com.fpmislata.basespring.controller.common.pagination.PaginatedResponse;
import com.fpmislata.basespring.domain.admin.model.BookAdmin;
import com.fpmislata.basespring.domain.admin.service.BookAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(BookAdminController.URL)
public class BookAdminController {

    public static final String URL = "/api/admin/books";
    private final BookAdminService bookAdminService;
    private final BaseControllerHelper baseControllerHelper;

    @GetMapping
    public ResponseEntity<PaginatedResponse<BookAdminCollection>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) Integer size) {

        int pageSize = baseControllerHelper.getPageSize(size);

        List<BookAdminCollection> books = bookAdminService.getAll(page - 1, pageSize)
                .stream()
                .map(BookAdminMapper.INSTANCE::toBookCollection)
                .toList();

        return baseControllerHelper.createPaginatedResponse(books, bookAdminService.count(), page, pageSize, URL);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<BookAdmin> findByIsbn(@PathVariable String isbn) {
        return new ResponseEntity<>(
                bookAdminService.findByIsbn(isbn),
                HttpStatus.OK
        );
    }


}
