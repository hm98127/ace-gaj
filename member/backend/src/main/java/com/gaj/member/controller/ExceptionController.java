package com.gaj.member.controller;


import com.gaj.member.dto.RestResponse;
import com.gaj.member.exception.MemberException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionController {
    private String VALIDATION_ERROR = "유효성 검증에 실패했습니다.";

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            BindException.class
    })
    public ResponseEntity<RestResponse> validationError(BindException exception) {
        RestResponse restResponse = RestResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(exception.getBindingResult().getAllErrors().stream().map(o -> o.getDefaultMessage()).collect(Collectors.joining()))
                .build();
        return new ResponseEntity<RestResponse>(restResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            MemberException.class
    })
    public ResponseEntity<RestResponse> memberNotExistError(MemberException exception) {
        RestResponse restResponse = RestResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<RestResponse>(restResponse, HttpStatus.BAD_REQUEST);
    }
}
