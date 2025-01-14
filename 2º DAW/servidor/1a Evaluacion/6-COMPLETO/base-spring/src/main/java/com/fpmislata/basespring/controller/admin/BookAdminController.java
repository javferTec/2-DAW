package com.fpmislata.basespring.controller.admin;

import com.fpmislata.basespring.controller.admin.adminModel.book.BookAdminCollection;
import com.fpmislata.basespring.controller.common.BaseController;
import com.fpmislata.basespring.controller.common.pagination.PaginatedResponse;
import com.fpmislata.basespring.domain.model.Author;
import com.fpmislata.basespring.domain.model.Book;
import com.fpmislata.basespring.domain.model.Genre;
import com.fpmislata.basespring.domain.useCase.book.admin.BookDeleteUseCase;
import com.fpmislata.basespring.domain.useCase.book.admin.BookInsertAuthorsUseCase;
import com.fpmislata.basespring.domain.useCase.book.admin.BookInsertGenresUseCase;
import com.fpmislata.basespring.domain.useCase.book.admin.BookInsertUseCase;
import com.fpmislata.basespring.domain.useCase.book.admin.impl.BookUpdateUseCaseImpl;
import com.fpmislata.basespring.domain.useCase.book.common.BookCountUseCase;
import com.fpmislata.basespring.domain.useCase.book.common.BookGetAllUseCase;
import com.fpmislata.basespring.domain.useCase.book.common.BookGetByIsbnUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;

@RestController
@RequiredArgsConstructor
@RequestMapping(BookAdminController.URL)
public class BookAdminController extends BaseController {

    public static final String URL = "/api/admin/books";

    private final BookGetAllUseCase bookGetAllUseCase;
    private final BookCountUseCase bookCountUseCase;
    private final BookGetByIsbnUseCase bookGetByIsbnUseCase;
    private final BookInsertAuthorsUseCase bookInsertAuthorsUseCase;
    private final BookInsertGenresUseCase bookInsertGenresUseCase;
    private final BookInsertUseCase bookInsertUseCase;
    private final BookUpdateUseCaseImpl bookUpdateUseCase;
    private final BookDeleteUseCase bookDeleteUseCase;

    @GetMapping
    public ResponseEntity<PaginatedResponse<BookAdminCollection>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) Integer size) {

        return super.getAll(
                page,
                size,
                offset -> bookGetAllUseCase.execute(offset, getPageSize(size)),
                book -> modelMapper.map(book, BookAdminCollection.class),
                bookCountUseCase.execute(),
                URL
        );
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getByIsbn(@PathVariable String isbn) {
        return super.getByIsbn(
                isbn,
                bookGetByIsbnUseCase::execute,
                Function.identity() // No se requiere transformación
        );
    }

    @PostMapping("/{id}/authors")
    public ResponseEntity<Void> insertAuthors(@PathVariable Integer id, @RequestBody List<Author> authors) {
        bookInsertAuthorsUseCase.execute(id, authors);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{id}/genres")
    public ResponseEntity<Void> insertGenres(@PathVariable Integer id, @RequestBody List<Genre> genres) {
        bookInsertGenresUseCase.execute(id, genres);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody Book book) {
        bookInsertUseCase.execute(book);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody Book book) {
        bookUpdateUseCase.execute(book);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        bookDeleteUseCase.execute(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
