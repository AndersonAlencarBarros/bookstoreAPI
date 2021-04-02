package com.dio.bookstore.builder;

import com.dio.bookstore.dto.BookDTO;
import lombok.Builder;

// auxilia nos testes, criando um objeto já preenchido
@Builder
public class BookDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Do Mil ao Milhao. Sem Cortar o Cafezinho";

    @Builder.Default
    private String author = "Thiago Nigro";

    @Builder.Default
    private String edição = "1ª";

    @Builder.Default
    private String editora = "HarperCollins";

    @Builder.Default
    private int numero_paginas = 192;

    @Builder.Default
    private String idioma = "Português";

    @Builder.Default
    private double preço = 14.90;

    public BookDTO toBookDTO() {
        return new BookDTO(
                id,
                name,
                author,
                edição,
                editora,
                numero_paginas,
                idioma,
                preço
        );
    }
}
