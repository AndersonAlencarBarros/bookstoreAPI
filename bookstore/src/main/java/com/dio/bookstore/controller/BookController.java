package com.dio.bookstore.controller;

import com.dio.bookstore.dto.BookDTO;
import com.dio.bookstore.entity.Book;
import com.dio.bookstore.exception.BookAlreadyRegisteredException;
import com.dio.bookstore.exception.BookNotFoundException;
import com.dio.bookstore.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

// Onde acontece toda a operação inicial do padrão REST
// @RestController indica que é uma classe controladora e
// entretanto, voltada para a API REST
// @RestController indica que os dados retornados por cada
// método serão gravados diretamente no corpo da resposta
@RestController
// Nome principal da API
@RequestMapping("/api/books")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookController{

    private final BookService bookService;

    // Temos rotas para cada operação @GetMapping, @PostMapping,
    // @PutMapping e @DeleteMapping
    // correspondendo a chamadas HTTP GET, POST, PUT e DELETE.

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO createBook(@RequestBody @Valid BookDTO bookDTO) throws BookAlreadyRegisteredException {
        return bookService.createBook(bookDTO);
    }

    @GetMapping
    public List<BookDTO> listBeers() {
        return bookService.listAll();
    }

    @GetMapping("/{name}")
    public BookDTO findByName(@PathVariable String name) throws BookNotFoundException {
        return bookService.findByName(name);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws BookNotFoundException {
        bookService.deleteById(id);
    }
}
