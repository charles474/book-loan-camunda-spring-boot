package com.example.camunda.book.loan.workflow.controller;

import com.example.camunda.book.loan.workflow.bpmn.BpmnBookProcessor;
import com.example.camunda.book.loan.workflow.model.Book;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1")
public class BookLoanController {

    private final BpmnBookProcessor bpmnBookProcessor;

    public BookLoanController(BpmnBookProcessor bpmnBookProcessor) {
        this.bpmnBookProcessor = bpmnBookProcessor;
    }

    @PostMapping(path = "/loanBook")
    public void loanBook(@RequestBody Book book){
        bpmnBookProcessor.loanBook(book);
    }
}
