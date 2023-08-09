package com.vv.task.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class CommonResponse<T> {
    private String message;
    private T data;

    public CommonResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>("ok", data);
    }

    public static <T> ResponseEntity<CommonResponse<T>> badRequest(String message) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new CommonResponse<>(message, null));
    }
}
