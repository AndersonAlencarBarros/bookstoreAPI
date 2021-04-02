package com.dio.bookstore.service;

import com.dio.bookstore.builder.BookDTOBuilder;
import com.dio.bookstore.dto.BookDTO;
import com.dio.bookstore.entity.Book;
import com.dio.bookstore.exception.BookAlreadyRegisteredException;
import com.dio.bookstore.exception.BookNotFoundException;
import com.dio.bookstore.mapper.BookMapper;
import com.dio.bookstore.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.empty;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

// Para rodar essa classe usa uma extensão do Mockito
// O testes aqui cobrem todos os casos de criação dos livros:
// Criação com sucesso
// Quando um livro já está cadastrado e etc.
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    private static final long INVALID_BEER_ID = 10L;

    @Mock  // cria um mock dessa classe
    private BookRepository bookRepository;

    private BookMapper bookMapper = BookMapper.INSTANCE;

    @InjectMocks
    private BookService bookService;

    @Test
    void whenBookInformedThenItShouldBeCreated() throws BookAlreadyRegisteredException {
        // given
        BookDTO expectedBookDTO = BookDTOBuilder.builder().build().toBookDTO();
        Book expectedSavedBook = bookMapper.toModel(expectedBookDTO);

        // when
        when(bookRepository.findByName(expectedBookDTO.getName())).thenReturn(Optional.empty());
        when(bookRepository.save(expectedSavedBook)).thenReturn(expectedSavedBook);

        //then
        BookDTO createdBookDTO = bookService.createBook(expectedBookDTO);

        // Hamcrest é responsável por deixar os testes mais limpos
        assertThat(createdBookDTO.getId(), is(equalTo(expectedBookDTO.getId())));
        assertThat(createdBookDTO.getName(), is(equalTo(expectedBookDTO.getName())));
        assertThat(createdBookDTO.getAuthor(), is(equalTo(expectedBookDTO.getAuthor())));
    }

    @Test
    void whenAlreadyRegisteredBookInformedThenAnExceptionShouldBeThrown() {
        // given
        BookDTO expectedBookDTO = BookDTOBuilder.builder().build().toBookDTO();
        Book duplicatedBook = bookMapper.toModel(expectedBookDTO);

        // when
        when(bookRepository.findByName(expectedBookDTO.getName())).thenReturn(Optional.of(duplicatedBook));

        // then
        assertThrows(BookAlreadyRegisteredException.class, () -> bookService.createBook(expectedBookDTO));
    }

    @Test
    void whenValidBookNameIsGivenThenReturnABook() throws BookNotFoundException {
        // given
        BookDTO expectedFoundBookDTO = BookDTOBuilder.builder().build().toBookDTO();
        Book expectedFoundBook = bookMapper.toModel(expectedFoundBookDTO);

        // when
        when(bookRepository.findByName(expectedFoundBook.getName())).thenReturn(Optional.of(expectedFoundBook));

        // then
        BookDTO foundBookDTO = bookService.findByName(expectedFoundBookDTO.getName());

        assertThat(foundBookDTO, is(equalTo(expectedFoundBookDTO)));
    }

    @Test
    void whenNotRegisteredBookNameIsGivenThenThrowAnException() {
        // given
        BookDTO expectedFoundBookDTO = BookDTOBuilder.builder().build().toBookDTO();

        // when
        when(bookRepository.findByName(expectedFoundBookDTO.getName())).thenReturn(Optional.empty());

        // then
        assertThrows(BookNotFoundException.class, () -> bookService.findByName(expectedFoundBookDTO.getName()));
    }

    @Test
    void whenListBookIsCalledThenReturnAListOfBooks() {
        // given
        BookDTO expectedFoundBookDTO = BookDTOBuilder.builder().build().toBookDTO();
        Book expectedFoundBook = bookMapper.toModel(expectedFoundBookDTO);

        //when
        when(bookRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundBook));

        //then
        List<BookDTO> foundListBooksDTO = bookService.listAll();

        assertThat(foundListBooksDTO, is(not(empty())));
        assertThat(foundListBooksDTO.get(0), is(equalTo(expectedFoundBookDTO)));
    }

    @Test
    void whenListBookIsCalledThenReturnAnEmptyListOfBooks() {
        //when
        when(bookRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        //then
        List<BookDTO> foundListBooksDTO = bookService.listAll();

        assertThat(foundListBooksDTO, is(empty()));
    }

    @Test
    void whenExclusionIsCalledWithValidIdThenABookShouldBeDeleted() throws BookNotFoundException{
        // given
        BookDTO expectedDeletedBookDTO = BookDTOBuilder.builder().build().toBookDTO();
        Book expectedDeletedBook = bookMapper.toModel(expectedDeletedBookDTO);

        // when
        when(bookRepository.findById(expectedDeletedBookDTO.getId())).thenReturn(Optional.of(expectedDeletedBook));
        doNothing().when(bookRepository).deleteById(expectedDeletedBookDTO.getId());

        // then
        bookService.deleteById(expectedDeletedBookDTO.getId());

        verify(bookRepository, times(1)).findById(expectedDeletedBookDTO.getId());
        verify(bookRepository, times(1)).deleteById(expectedDeletedBookDTO.getId());
    }

}
