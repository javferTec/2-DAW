package com.fpmislata.basespring.controller;

import com.fpmislata.basespring.controller.webMapper.book.BookMapper;
import com.fpmislata.basespring.controller.webPagination.PaginatedResponse;
import com.fpmislata.basespring.controller.webModel.book.BookCollection;
import com.fpmislata.basespring.controller.webModel.book.BookDetail;
import com.fpmislata.basespring.domain.service.BookService;
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
@RequestMapping(BookController.URL)
public class BookController {
    @Value("${app.base.url}")
    private String baseUrl;

    @Value("${app.pageSize.default}")
    private String defaultPageSize;

    public static final String URL = "/api/books";
    private final BookService bookService;

    @RequestMapping
    public ResponseEntity<PaginatedResponse<BookCollection>> findAll(
            @RequestParam(defaultValue = "1") int page, // obtiene el número de página
            @RequestParam(required = false) Integer size) { // obtiene el tamaño de la página

        int pageSize = (size != null) ? size : Integer.parseInt(defaultPageSize); // si el tamaño de la página no es nulo, se asigna el tamaño de la página, de lo contrario, se asigna el tamaño de la página por defecto
        List<BookCollection> books = bookService // obtiene el servicio de libros
                .findAll(page - 1, pageSize) // obtiene los libros paginados
                .stream() // convierte el flujo de datos en un Stream (stream es una secuencia de elementos)
                .map(BookMapper.INSTANCE::toBookCollection) // mapea de Book a BookCollection
                .toList(); // convierte de BookCollection a una lista

        int total = bookService.count(); // obtiene el número total de libros

        PaginatedResponse<BookCollection> response = new PaginatedResponse<>(books, total, page, pageSize, baseUrl + URL); // crea la respuesta paginada
        return new ResponseEntity<>(response, HttpStatus.OK); // devuelve la respuesta paginada + el código de estado HTTP
    }

    @RequestMapping("/{isbn}")
    public ResponseEntity<BookDetail> findByIsbn(@PathVariable("isbn") String isbn) {
        BookDetail bookDetail = BookMapper.INSTANCE.toBookDetail(bookService.findByIsbn(isbn));
        return new ResponseEntity<>(bookDetail, HttpStatus.OK);
    }
}

// ResponseEntity representa una respuesta HTTP, incluyendo el código de estado, las cabeceras y el cuerpo.