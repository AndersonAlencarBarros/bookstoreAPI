package com.dio.bookstore.mapper;

import com.dio.bookstore.dto.BookDTO;
import com.dio.bookstore.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper // mapeia a classe DTO para a classe original
// DTO é um objeto que transporta dados entre processos
// para reduzir o número de chamadas de método
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    Book toModel(BookDTO bookDTO);

    BookDTO toDTO(Book book);
}
