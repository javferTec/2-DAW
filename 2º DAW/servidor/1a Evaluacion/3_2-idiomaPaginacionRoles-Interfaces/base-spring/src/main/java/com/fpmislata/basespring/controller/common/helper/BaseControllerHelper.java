package com.fpmislata.basespring.controller.common.helper;

import com.fpmislata.basespring.controller.common.pagination.PaginatedResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class BaseControllerHelper {

    @Value("${app.base.url}")
    private String baseUrl;

    @Value("${app.pageSize.default}")
    private String defaultPageSize;

    public int getPageSize(Integer size) {
        return (size != null) ? size : Integer.parseInt(defaultPageSize);
    }

    public <T> ResponseEntity<PaginatedResponse<T>> createPaginatedResponse(
            List<T> items, int total, int page, int pageSize, String url) {

        PaginatedResponse<T> response = new PaginatedResponse<>(items, total, page, pageSize, baseUrl + url);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}