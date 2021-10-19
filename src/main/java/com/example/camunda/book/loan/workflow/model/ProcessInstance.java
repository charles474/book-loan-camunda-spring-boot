package com.example.camunda.book.loan.workflow.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ProcessInstance {

    LOAN_BOOK_PROCESS_INSTANCE("book-loan-process");

    @Getter
    private String name;
}
