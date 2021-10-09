package com.example.camunda.book.loan.repository;

import com.example.camunda.book.loan.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    public Optional<Book> findByTitleIgnoreCase(String title);

}
