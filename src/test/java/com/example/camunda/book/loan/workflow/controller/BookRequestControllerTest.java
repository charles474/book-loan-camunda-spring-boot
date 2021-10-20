package com.example.camunda.book.loan.workflow.controller;

import com.example.camunda.book.loan.workflow.bpmn.BpmnBookProcessor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

class BookRequestControllerTest {

    private BpmnBookProcessor bpmnBookProcessor = Mockito.mock(BpmnBookProcessor.class);
    private BookRequestController controller = new BookRequestController(bpmnBookProcessor);

    @Test
    public void shouldVerifyThatTheLoanMethodWasInvoked(){
        // When
        controller.loanBook(null);

        // Then
        verify(bpmnBookProcessor).loanBook(null);
    }

    @Test
    public void shouldVerifyThatTheReturnBookMethodWasInvoked(){
        // When
        controller.returnBook(null);

        // Then
        verify(bpmnBookProcessor).returnBook(null);
    }

}