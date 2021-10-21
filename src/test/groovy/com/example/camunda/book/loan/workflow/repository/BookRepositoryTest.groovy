package com.example.camunda.book.loan.workflow.repository

import com.example.camunda.book.loan.workflow.model.Book
import spock.lang.Specification

class BookRepositoryTest extends Specification{

    def 'should find book by title'(String title){
        given: "a book object is supplied"
        def book = new Book(title, 1);

        and: "a mock of the book repository that returns the same book"
        BookRepository bookRepository = Stub(BookRepository)
        bookRepository.findByTitleIgnoreCase(title) >> Optional.of(book);

        when:
        def bookOptional = bookRepository.findByTitleIgnoreCase(title);

        then:
        bookOptional.get() == book;

        // Provide our parameters for the parameterized test in the where block
        where:
            // Name of method parameter, the underscore "_" is just a placeholder as we're only using 1 value for the parameter test
            title | _
            "Oliver Twist" | _
            "Alice In Wonderland" | _
    }

    def 'should not find book by title'(String title){
        given: "a mock of the book repository that returns the same book"
        def bookRepository = Stub(BookRepository)
        bookRepository.findByTitleIgnoreCase(title) >> Optional.empty();

        when: "after book repository searches and result is called for"
        bookRepository.findByTitleIgnoreCase(title).get();

        then: "when not found, should throw an exception of a particular type"
        thrown NoSuchElementException;

        // Provide our parameters for the parameterized test in the where block
        where:
        // Name of method parameter, the underscore "_" is just a placeholder as we're only using 1 value for the parameter test
        title | _
        "Oliver Twist" | _
        "Alice In Wonderland" | _
    }

}
