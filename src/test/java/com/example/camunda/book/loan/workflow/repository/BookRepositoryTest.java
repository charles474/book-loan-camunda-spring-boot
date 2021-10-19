package com.example.camunda.book.loan.workflow.repository;

import com.example.camunda.book.loan.BookLoanCamundaSpringBootApplication;
import com.example.camunda.book.loan.workflow.model.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BookLoanCamundaSpringBootApplication.class)
class BookRepositoryTest {

    @Autowired
    private BookRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @ParameterizedTest
    @MethodSource("getBooksToLoan")
    void shouldFindBookByTitle(String bookTitle) {
        //Given
        Book book = new Book(bookTitle, 1);
        underTest.save(book);

        // When
        Optional<Book> bookOptional = underTest.findByTitleIgnoreCase(bookTitle);

        // Then
        assertThat(bookOptional.get()).isNotNull();
    }

    @ParameterizedTest
    @MethodSource("getBooksToLoan")
    void shouldNotFindBookByTitle(String bookTitle) {
        // When
        Optional<Book> bookOptional = underTest.findByTitleIgnoreCase(bookTitle);

        // Then
        //assertThatThrownBy(() -> bookOptional.get()).isInstanceOf(NoSuchElementException.class);
        assertThat(bookOptional.isPresent()).isFalse();
    }

    private static Stream<Arguments> getBooksToLoan(){
        return Stream.of(
                Arguments.of("Oliver Twist"),
                Arguments.of("Alice In Wonderland")
        );
    }
}