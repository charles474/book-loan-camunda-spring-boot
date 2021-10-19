package com.example.camunda.book.loan.workflow.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Book {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private Integer bookCount;

    public Book(String title, Integer bookCount){
        this.title = title;
        this.bookCount = bookCount;
    }

}
