package com.example.camunda.book.loan.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

@Slf4j
public class OutOfStockDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String bookTitle = (String) execution.getVariable("title");
        log.info("Out Of Stock (or Not found): {}", bookTitle);
    }
}
