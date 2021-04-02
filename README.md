# Book Store API

Desenvolvimento de uma simples API REST para uma Livraria.

Na API é possível

- Adicionar um livro
- Remover um livro
- Retornar um livro pelo nome
- Deletar um livro pelo ID

Projeto recriado com base no projeto *Desenvolvimento de testes unitários para validar uma API REST de gerenciamento de estoques de cerveja* do bootcamp *Inter Java Developer*.

O projeto segue simples inicialmente, porém pretendo a continuar melhorá-lo no futuro próximo. 

O projeto foi desenvolvido com Java 14, Spring Boot e Spring MVC, os frameworks de testes JUnit, Mockito e Hamcrest e as bibliotecas Lombok e MapStruct, além do Postman para testes e a IDE IntelliJ IDEA.

## Executando o projeto...

Para executar o projeto pode-se digitar no terminal

```
mvn spring-boot:run 
```

e os testes com 

```
mvn clean test
```

*Infelizmente os testes com GET não executaram de forma desejada, apresentando um erro desconhecido.*

Pode-se também executar o projeto pela IDE executando a classe `BookstoreApplication`

<img src="./attachments/run%20project.gif" alt="run project" style="zoom: 67%;" />



## Documentação

Uma simples visão da API está no Postman e pode ser vista no [link](https://documenter.getpostman.com/view/15216675/TzCP77T5). Para testar pelo Postman, primeiro execute o projeto e faça as requisições HTTP.

### Adicionar um livro

Para adicionar um livro, realize uma requisição POST com o JSON com os dados do livro.

<img src="./attachments/add%20a%20book.gif" alt="add a book" style="zoom: 67%;" />

### Retornar um livro pelo nome

Para retornar os dados de um livro pelo nome, realize uma requisição GET com o nome do livro como parâmetro.

<img src="./attachments/get%20a%20book%20by%20name.gif" alt="get a book by name" style="zoom: 67%;" />

### Retornar todos os livros

Para retornar todos os livros basta realizar uma requisição GET sem parâmetros.

<img src="./attachments/get%20all%20books.gif" alt="get all books" style="zoom: 67%;" />

### Remover um livro pelo ID

Para remover pelo ID, realize uma requisição DELETE com o ID como parâmetro.

<img src="./attachments/delete%20a%20book.gif" alt="delete a book" style="zoom: 67%;" />