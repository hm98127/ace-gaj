package com.gaj.member.controller;


import com.gaj.member.dto.RestResponse;
import com.gaj.member.exception.MemberException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class
    })
    public ResponseEntity<RestResponse> methodNotAllowedError() {
        RestResponse restResponse = RestResponse.builder()
                .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                .message(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase())
                .build();
        return new ResponseEntity<RestResponse>(restResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            BindException.class
    })
    public ResponseEntity<RestResponse> validationError(BindException exception) {

        RestResponse restResponse = RestResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exception.getBindingResult().getAllErrors().stream().map(o -> o.getDefaultMessage()).collect(Collectors.joining()))
                .build();
        return new ResponseEntity<RestResponse>(restResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            MemberException.class
    })
    public ResponseEntity<RestResponse> memberNotExistError(MemberException exception) {

        RestResponse restResponse = RestResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<RestResponse>(restResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            NoHandlerFoundException.class
    })
    public ResponseEntity<RestResponse> noHandlerFoundError(NoHandlerFoundException exception, WebRequest request) {

        RestResponse restResponse = RestResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(HttpStatus.NOT_FOUND.getReasonPhrase())
                .build();
        return new ResponseEntity<RestResponse>(restResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            Exception.class
    })
    public ResponseEntity<RestResponse> remainError(Exception exception) {

        RestResponse restResponse = RestResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .build();
        return new ResponseEntity<RestResponse>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
