package com.example.camunda.book.loan.workflow.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

@Slf4j
public class LoanBook implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("Loan Accepted: {}", execution.getVariable("title"));
    }
}
