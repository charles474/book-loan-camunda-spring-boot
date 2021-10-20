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

class StockCheckerTest {

    private BookRepository bookRepository = mock(BookRepository.class);
    private final StockChecker stockChecker = new StockChecker(bookRepository);
    private LogCaptor logCaptor = LogCaptor.forClass(StockChecker.class);

    @Test
    public void shouldLog() throws Exception {
        //Given
        final String bookTitle = "Alice In Wonderland";
        Book book = new Book(1L, bookTitle, 0);
        DelegateExecution execution = mock(DelegateExecution.class);
        when(execution.getVariable("title")).thenReturn(bookTitle);
        when(bookRepository.findByTitleIgnoreCase(bookTitle)).thenReturn(Optional.of(book));

        //When
        stockChecker.execute(execution);
        List<String> logs = logCaptor.getLogs();

        //Then
        assertThat(logs.get(0)).isEqualTo(String.format("Checking stock for Book: %s", bookTitle));
    }

}