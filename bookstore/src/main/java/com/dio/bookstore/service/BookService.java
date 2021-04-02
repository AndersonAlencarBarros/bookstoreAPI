package com.dio.bookstore.service;

import com.dio.bookstore.dto.BookDTO;
import com.dio.bookstore.entity.Book;
import com.dio.bookstore.exception.BookAlreadyRegisteredException;
import com.dio.bookstore.exception.BookNotFoundException;
import com.dio.bookstore.mapper.BookMapper;
import com.dio.bookstore.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// @Service indica que todo o ciclo de vida dessa classe é gerenciada pelo Spring
// Para utilizar na classe no controller basta apenar indicar esta classe lá
// e o Spring irá gerenciá-la.
@Service
// do Lombok cria um construtor e ajuda na manutenção do código
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper = BookMapper.INSTANCE;

    private void verifyIfAlreadyRegistered(String name) throws BookAlreadyRegisteredException {
        Optional<Book> optBook = bookRepository.findByName(name);
        if (optBook.isPresent()) {
            throw new BookAlreadyRegisteredException(name);
        }
    }

    private Book verifyIfExists(Long id) throws BookNotFoundException {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public BookDTO createBook(BookDTO bookDTO) throws BookAlreadyRegisteredException {
        verifyIfAlreadyRegistered(bookDTO.getName());
        Book book = bookMapper.toModel(bookDTO);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDTO(savedBook);
    }

    public BookDTO findByName(String name) throws BookNotFoundException {
        Book foundBook = bookRepository.findByName(name)
                .orElseThrow(() -> new BookNotFoundException(name));

        return bookMapper.toDTO(foundBook);
    }

    public void deleteById(Long id) throws BookNotFoundException {
        verifyIfExists(id);
        bookRepository.deleteById(id);
    }

    public List<BookDTO> listAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }
}
