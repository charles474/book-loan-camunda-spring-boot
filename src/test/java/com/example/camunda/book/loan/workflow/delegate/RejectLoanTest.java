package com.example.camunda.book.loan.workflow.delegate;

import nl.altindag.log.LogCaptor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;

class RejectLoanTest {

    private LogCaptor logCaptor = LogCaptor.forClass(RejectLoan.class);
    private final RejectLoan rejectLoan = new RejectLoan();

    @Test
    public void shouldLog() throws Exception {
        //Given
        DelegateExecution execution = mock(DelegateExecution.class);

        // When
        rejectLoan.execute(execution);
        List<String> logs = logCaptor.getLogs();

        //Then
        assertThat(logs.get(0)).isEqualTo("No book title provided, loan Rejected");
    }

}