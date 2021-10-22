package com.example.camunda.book.loan.workflow.controller

import com.example.camunda.book.loan.workflow.bpmn.BpmnBookProcessor
import spock.lang.Specification

class BookRequestControllerTest extends Specification{

    def "should verify loan book method was invoked"(){
        given: "a mock of bpmn processor"
        def bpmnBookProcessor = Mock(BpmnBookProcessor)
        def controller = new BookRequestController(bpmnBookProcessor);

        when: "controller loanBook method is invoked"
        controller.loanBook(null);

        then: "verify that bpmnProcessor's loanBook method was invoked once"
        1 * bpmnBookProcessor.loanBook(null);
    }

    def "should verify return book method was invoked"(){
        given: "a mock of bpmn processor and an object of BookRequestController"
        def bpmnBookProcessor = Mock(BpmnBookProcessor)
        def controller = new BookRequestController(bpmnBookProcessor);

        when: "controller returnBook method is invoked"
        controller.returnBook(null);

        then: "verify that bpmnProcessor's returnBook method was invoked once"
        1 * bpmnBookProcessor.returnBook(null);
    }
}
