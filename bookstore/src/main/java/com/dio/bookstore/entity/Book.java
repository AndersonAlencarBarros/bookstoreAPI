package com.dio.bookstore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data       // gera getters e setters, equals e hashCode
@Entity     // é uma anotação JPA para tornar este objeto pronto para armazenamento de dados baseado em JPA.
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id // chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String edição;

    @Column(nullable = false)
    private String editora;

    @Column(nullable = false)
    private int numero_paginas;

    @Column(nullable = false)
    private String idioma;

    @Column(nullable = false)
    private double preço;
}
