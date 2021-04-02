package com.dio.bookstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookAlreadyRegisteredException extends Exception{

    public BookAlreadyRegisteredException(String book) {
        super(String.format("Book %s already registered", book));
    }
}
