package com.example.camunda.book.loan.workflow.delegate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum BookLimit {

    MAX_BOOK_LIMIT(10);

    @Getter
    private int limit;
}
