package com.example.camunda.book.loan.workflow.delegate;

import com.example.camunda.book.loan.workflow.model.Book;
import com.example.camunda.book.loan.workflow.repository.BookRepository;
import nl.altindag.log.LogCaptor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReturnBookTest {

    private final BookRepository bookRepository = mock(BookRepository.class);
    private final ReturnBook returnBook = new ReturnBook(bookRepository);
    private LogCaptor logCaptor = LogCaptor.forClass(ReturnBook.class);

    @Test
    public void shouldLogBookReturnSuccessful() throws Exception {
        //Given
        final String bookTitle = "Alice In Wonderland";
        DelegateExecution execution = mock(DelegateExecution.class);
        when(execution.getVariable("title")).thenReturn(bookTitle);
        when(bookRepository.findByTitleIgnoreCase(bookTitle)).thenReturn(Optional.of(new Book(1L, bookTitle, 0)));

        //When
        returnBook.execute(execution);
        List<String> logs = logCaptor.getLogs();

        //Then
        assertThat(logs.get(0)).isEqualTo(String.format("Book return successful for '%s'", bookTitle));
        assertThat(logs.get(1)).isEqualTo(String.format("Stock count for '%s', %s book(s)", bookTitle, 1));
    }

    @Test
    public void shouldLogBookDoesNotBelongToLibrary() throws Exception {
        //Given
        final String bookTitle = "Alice In Wonderland";
        DelegateExecution execution = mock(DelegateExecution.class);
        when(execution.getVariable("title")).thenReturn(bookTitle);
        when(bookRepository.findByTitleIgnoreCase(bookTitle)).thenReturn(Optional.empty());

        //When
        returnBook.execute(execution);
        List<String> logs = logCaptor.getLogs();

        //Then
        assertThat(logs.get(0)).isEqualTo(String.format("Book '%s' does not belong to this library", bookTitle));
    }

    @Test
    public void shouldLogReturnUnsuccessful() throws Exception {
        //Given
        final String bookTitle = "Alice In Wonderland";
        DelegateExecution execution = mock(DelegateExecution.class);
        when(execution.getVariable("title")).thenReturn(bookTitle);
        when(bookRepository.findByTitleIgnoreCase(bookTitle)).thenReturn(Optional.of(new Book(1L, bookTitle, 10)));

        //When
        returnBook.execute(execution);
        List<String> logs = logCaptor.getLogs();

        //Then
        assertThat(logs.get(0)).isEqualTo(String.format("Book return unsuccessful for '%s'", bookTitle));
    }

}