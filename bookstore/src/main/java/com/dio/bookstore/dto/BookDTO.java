package com.dio.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String author;

    @NotNull
    private String edição;

    @NotNull
    private String editora;

    @NotNull
    private int numero_paginas;

    @NotNull
    private String idioma;

    @NotNull
    private double preço;
}
