package com.vv.task.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("[{}] handled : {} ", ex.getClass().getSimpleName(), ex.getMessage());
        return super.handleMethodArgumentNotValid(ex, headers, status, request);
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<CommonResponse<Object>> globalErrorHandle(Exception ex) {
        log.error("[{}] handled : {} ", ex.getClass().getSimpleName(), ex.getMessage());
        String errorMessage = ex.getMessage();
        return CommonResponse.badRequest(errorMessage != null ? errorMessage : "entity not found exception");
    }
}
