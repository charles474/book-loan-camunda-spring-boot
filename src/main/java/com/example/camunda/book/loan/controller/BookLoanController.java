package com.example.camunda.book.loan.controller;

import com.example.camunda.book.loan.bpmn.BpmnProcessor;
import com.example.camunda.book.loan.model.Book;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1")
public class BookLoanController {

    private final BpmnProcessor bpmnProcessor;

    public BookLoanController(BpmnProcessor bpmnProcessor) {
        this.bpmnProcessor = bpmnProcessor;
    }

    @PostMapping(path = "/loanBook")
    public void loanBook(@RequestBody Book book){
        bpmnProcessor.loanBook(book);
    }
}
