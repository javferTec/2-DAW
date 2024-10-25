package com.fpmislata.basespring.common.http_errors;

import com.fpmislata.basespring.common.layers.domain.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            ResourceNotFoundException.class
    })
    @ResponseBody
    public ErrorMessage notFoundRequest(ResourceNotFoundException exception) {
        return new ErrorMessage(exception);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorMessage handleGeneralException(Exception exception) {
        return new ErrorMessage(exception);
    }
}
