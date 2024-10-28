package com.fpmislata.basespring.controller.user;

import com.fpmislata.basespring.controller.user.userMapper.book.BookUserMapper;
import com.fpmislata.basespring.controller.common.pagination.PaginatedResponse;
import com.fpmislata.basespring.controller.user.userModel.book.BookUserCollection;
import com.fpmislata.basespring.controller.user.userModel.book.BookUserDetail;
import com.fpmislata.basespring.domain.user.service.BookUserService;
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
            @RequestParam(defaultValue = "1") int page, // obtiene el número de página
            @RequestParam(required = false) Integer size) { // obtiene el tamaño de la página

        int pageSize = (size != null) ? size : Integer.parseInt(defaultPageSize); // si el tamaño de la página no es nulo, se asigna el tamaño de la página, de lo contrario, se asigna el tamaño de la página por defecto
        List<BookUserCollection> books = bookUserService // obtiene el servicio de libros
                .findAll(page - 1, pageSize) // obtiene los libros paginados
                .stream() // convierte el flujo de datos en un Stream (stream es una secuencia de elementos)
                .map(BookUserMapper.INSTANCE::toBookCollection) // mapea de Book a BookCollection
                .toList(); // convierte de BookCollection a una lista

        int total = bookUserService.count(); // obtiene el número total de libros

        PaginatedResponse<BookUserCollection> response = new PaginatedResponse<>(books, total, page, pageSize, baseUrl + URL); // crea la respuesta paginada
        return new ResponseEntity<>(response, HttpStatus.OK); // devuelve la respuesta paginada + el código de estado HTTP
    }

    @RequestMapping("/{isbn}")
    public ResponseEntity<BookUserDetail> findByIsbn(@PathVariable("isbn") String isbn) {
        BookUserDetail bookUserDetail = BookUserMapper.INSTANCE.toBookDetail(bookUserService.findByIsbn(isbn));
        return new ResponseEntity<>(bookUserDetail, HttpStatus.OK);
    }
}

// ResponseEntity representa una respuesta HTTP, incluyendo el código de estado, las cabeceras y el cuerpo.