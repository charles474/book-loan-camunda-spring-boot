package com.example.camunda.book.loan.workflow.repository;

import com.example.camunda.book.loan.workflow.model.Book;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BookRepositoryTest {

    private final BookRepository mockBookRepository = mock(BookRepository.class);

    @ParameterizedTest
    @MethodSource("getBooksToLoan")
    void shouldFindBookByTitle(String bookTitle) {
        //Given
        Book book = new Book(bookTitle, 1);
        when(mockBookRepository.findByTitleIgnoreCase(bookTitle)).thenReturn(Optional.of(book));

        // When
        Optional<Book> bookOptional = mockBookRepository.findByTitleIgnoreCase(bookTitle);

        // Then
        assertThat(bookOptional.get()).isEqualTo(book);
    }

    @ParameterizedTest
    @MethodSource("getBooksToLoan")
    void shouldNotFindBookByTitle(String bookTitle) {
        // When
        when(mockBookRepository.findByTitleIgnoreCase(bookTitle)).thenReturn(Optional.empty());

        //Then
        Optional<Book> bookOptional = mockBookRepository.findByTitleIgnoreCase(bookTitle);

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