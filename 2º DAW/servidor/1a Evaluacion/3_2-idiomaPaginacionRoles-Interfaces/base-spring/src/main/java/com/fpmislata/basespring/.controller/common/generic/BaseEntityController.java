package com.fpmislata.basespring.controller.common.generic;

import com.fpmislata.basespring.controller.common.pagination.PaginatedResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public abstract class BaseEntityController<CollectionType, DetailType, ModelType> {

    @Value("${app.base.url}")
    private String baseUrl;

    @Value("${app.pageSize.default}")
    private String defaultPageSize;

    public abstract String getUrlController();
    public abstract List<ModelType> getAll(int page, int pageSize);
    public abstract int count();
    public abstract CollectionType toCollection(ModelType model);
    public abstract DetailType toDetail(String isbn);

    @GetMapping
    public ResponseEntity<PaginatedResponse<CollectionType>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) Integer size) {

        int pageSize = (size != null) ? size : Integer.parseInt(defaultPageSize);
        List<CollectionType> entities = getAll(page - 1, pageSize).stream()
                .map(this::toCollection).toList();
        int total = count();

        PaginatedResponse<CollectionType> response = new PaginatedResponse<>(entities, total, page, pageSize, baseUrl + getUrlController());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<DetailType> findById(@PathVariable("isbn") String isbn) {
        DetailType detail = toDetail(isbn);
        return new ResponseEntity<>(detail, HttpStatus.OK);
    }
}