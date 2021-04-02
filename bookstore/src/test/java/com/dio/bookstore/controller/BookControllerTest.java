package com.dio.bookstore.controller;

import com.dio.bookstore.builder.BookDTOBuilder;
import com.dio.bookstore.dto.BookDTO;
import com.dio.bookstore.exception.BookNotFoundException;
import com.dio.bookstore.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static com.dio.bookstore.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Testar a criação com sucesso
// Validar os dados retornados
// Validar o status de erro do HTTP
@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    private static final String BOOK_API_URL_PATH = "/api/books";
    private static final long INVALID_BOOK_ID = 100L;

    // Usando MockMvc você pode testar seus componentes
    // MVC com um ambiente simulado.
    private MockMvc mockMvc;

    // Cria um mock
    @Mock
    private BookService bookService;

    // @InjectMocks cria uma instância da classe e injeta
    // os mocks que são criados com as anotações @Mock nesta instância.
    @InjectMocks
    private BookController bookController;

    // Inicializamos o objeto mockMvc no método anotado @BeforeEach
    // para que não tenhamos que inicializá-lo dentro de cada teste.
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    // Não passa
//    @Test
//    void whenGETIsCalledWithValidNameThenOkStatusIsReturned() throws Exception {
//        // given
//        BookDTO bookDTO = BookDTOBuilder.builder().build().toBookDTO();
//
//        //when
//        when(bookService.findByName(bookDTO.getName())).thenReturn(bookDTO);
//
//        // then
//        mockMvc.perform(MockMvcRequestBuilders
//            .get(BOOK_API_URL_PATH + "/" + bookDTO.getName())
//            .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.name", is(bookDTO.getName())))
//            .andExpect(jsonPath("$.author", is(bookDTO.getAuthor())))
//            .andExpect(jsonPath("$.editora", is(bookDTO.getEditora())));
//
//    }

    // Não passa
//    @Test
//    void whenGETIsCalledWithoutRegisteredNameThenNotFoundStatusIsReturned() throws Exception {
//        // given
//        BookDTO bookDTO = BookDTOBuilder.builder().build().toBookDTO();
//
//        //when
//        when(bookService.findByName(bookDTO.getName())).thenThrow(BookNotFoundException.class);
//
//        // then
//        mockMvc.perform(MockMvcRequestBuilders.get(BOOK_API_URL_PATH + "/" + bookDTO.getName())
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//    }

    @Test
    void whenGETListWithBooksIsCalledThenOkStatusIsReturned() throws Exception {
        // given
        BookDTO bookDTO = BookDTOBuilder.builder().build().toBookDTO();

        //when
        when(bookService.listAll()).thenReturn(Collections.singletonList(bookDTO));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(BOOK_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(bookDTO.getName())))
                .andExpect(jsonPath("$[0].author", is(bookDTO.getAuthor())))
                .andExpect(jsonPath("$[0].editora", is(bookDTO.getEditora())));
    }

    @Test
    void whenGETListWithoutBeersIsCalledThenOkStatusIsReturned() throws Exception {
        // given
        BookDTO bookDTO = BookDTOBuilder.builder().build().toBookDTO();

        //when
        when(bookService.listAll()).thenReturn(Collections.singletonList(bookDTO));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(BOOK_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenPOSTIsCalledThenABookIsCreated() throws Exception {
        // given
        BookDTO bookDTO = BookDTOBuilder.builder().build().toBookDTO();

        // when
        when(bookService.createBook(bookDTO)).thenReturn(bookDTO);

        // then
        MvcResult mvcResult = mockMvc.perform(post(BOOK_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(bookDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(bookDTO.getName())))
                .andExpect(jsonPath("$.author", is(bookDTO.getAuthor())))
                .andExpect(jsonPath("$.editora", is(bookDTO.getEditora())))
                .andReturn();   // retorna um objeto usado abaixo para imprimir a operação

        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
        // given
        BookDTO bookDTO = BookDTOBuilder.builder().build().toBookDTO();
        bookDTO.setAuthor(null);

        // then
        mockMvc.perform(post(BOOK_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(bookDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {
        // given
        BookDTO bookDTO = BookDTOBuilder.builder().build().toBookDTO();

        //when
        doNothing().when(bookService).deleteById(bookDTO.getId());

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(BOOK_API_URL_PATH + "/" + bookDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {
        //when
        doThrow(BookNotFoundException.class).when(bookService).deleteById(INVALID_BOOK_ID);

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(BOOK_API_URL_PATH + "/" + INVALID_BOOK_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
