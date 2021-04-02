package com.dio.bookstore.repository;

import com.dio.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Esta classe tem o dever de conversar com o banco de dados
// Criação, leitura, atualização, remoção e busca no banco de dados
// Está é uma classe DAO - Data Access Object
// Ela estende o JpaRepository, sendo necessário especificar o tipo de dado,
// neste caso Book e o tipo do id, Long
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByName(String name);
}

