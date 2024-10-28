package com.fpmislata.basespring.controller.common.base;

import com.fpmislata.basespring.controller.common.pagination.PaginatedResponse;
import com.fpmislata.basespring.domain.common.base.BaseBookService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public abstract class BaseBookController<T, C, D> { // T = Entidad // C = Coleccion // D = Detalle

    protected abstract BaseBookService<T> getService();
    protected abstract Mapper<T, C, D> getMapper();
    protected abstract String getUrl();

    @Value("${app.pageSize.default}")
    private String defaultPageSize;

    @Value("${app.base.url}")
    private String baseUrl;

    @GetMapping
    public ResponseEntity<PaginatedResponse<C>> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) Integer size) {

        int pageSize = (size != null) ? size : Integer.parseInt(defaultPageSize);
        List<C> books = getService()
                .findAll(page - 1, pageSize)
                .stream()
                .map(getMapper()::toCollection)
                .toList();

        int total = getService().count();

        PaginatedResponse<C> response = new PaginatedResponse<>(books, total, page, pageSize, baseUrl + getUrl());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<D> findByIsbn(@PathVariable("isbn") String isbn) {
        D bookDetail = getMapper().toDetail(getService().findByIsbn(isbn));
        return ResponseEntity.ok(bookDetail);
    }
}