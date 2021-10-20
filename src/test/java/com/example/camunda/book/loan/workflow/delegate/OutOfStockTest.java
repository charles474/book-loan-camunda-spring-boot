package com.example.camunda.book.loan.workflow.delegate;

import nl.altindag.log.LogCaptor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OutOfStockTest {

    private final OutOfStock outOfStock = new OutOfStock();
    private LogCaptor logCaptor = LogCaptor.forClass(OutOfStock.class);

    @Test
    public void shouldLog() throws Exception {
        //Given
        final String bookTitle = "Alice In Wonderland";
        DelegateExecution execution = mock(DelegateExecution.class);
        when(execution.getVariable("title")).thenReturn(bookTitle);
        when(execution.getVariable("available")).thenReturn(false);

        //When
        outOfStock.execute(execution);
        List<String> logs = logCaptor.getLogs();

        //Then
        assertThat(logs.get(0)).isEqualTo(String.format("Out Of Stock (or Not found): %s", bookTitle));
    }

}