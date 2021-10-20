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

class LoanBookTest {

    private BookRepository bookRepository = mock(BookRepository.class);
    private final LoanBook loanBook = new LoanBook(bookRepository);
    private LogCaptor logCaptor = LogCaptor.forClass(LoanBook.class);


    @Test
    public void shouldLog() throws Exception {
        //Given
        final String bookTitle = "Alice In Wonderland";
        DelegateExecution execution = mock(DelegateExecution.class);
        when(bookRepository.findByTitleIgnoreCase(bookTitle)).thenReturn(Optional.of(new Book(1L, bookTitle, 3)));
        when(execution.getVariable("title")).thenReturn(bookTitle);
        when(execution.getVariable("available")).thenReturn(true);

        //When
        loanBook.execute(execution);
        List<String> logs = logCaptor.getInfoLogs();

        //Then
        assertThat(logs.get(0)).isEqualTo(String.format("Loan Accepted: %s", bookTitle));
        assertThat(logs.get(1)).contains(String.format("Book: %s, Stock book count:", bookTitle));
        assertThat(logs.get(2)).contains(String.format("Book: %s, New remaining count:", bookTitle));
    }

}