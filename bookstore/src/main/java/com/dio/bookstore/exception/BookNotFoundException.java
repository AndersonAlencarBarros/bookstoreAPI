package com.dio.bookstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotFoundException extends Exception{

    public BookNotFoundException(String bookName) {
        super(String.format("Book %s not found", bookName));
    }

    public BookNotFoundException(Long id) {
        super(String.format("Book %s not found", Double.toString(id)));
    }
}
