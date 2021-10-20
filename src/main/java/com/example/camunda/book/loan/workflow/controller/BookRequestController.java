package com.example.camunda.book.loan.workflow.controller;

import com.example.camunda.book.loan.workflow.bpmn.BpmnBookProcessor;
import com.example.camunda.book.loan.workflow.model.Book;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1")
public class BookRequestController {

    private final BpmnBookProcessor bpmnBookProcessor;

    public BookRequestController(BpmnBookProcessor bpmnBookProcessor) {
        this.bpmnBookProcessor = bpmnBookProcessor;
    }

    @GetMapping(path = "/loanBook")
    public void loanBook(@RequestBody Book book){
        bpmnBookProcessor.loanBook(book);
    }

    @PostMapping(path = "/returnBook")
    public void returnBook(@RequestBody Book book){
        bpmnBookProcessor.returnBook(book);
    }
}
