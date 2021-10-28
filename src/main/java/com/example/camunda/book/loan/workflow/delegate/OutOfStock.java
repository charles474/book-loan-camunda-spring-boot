package com.example.camunda.book.loan.workflow.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import static com.example.camunda.book.loan.workflow.delegate.Status.AVAILABLE;
import static com.example.camunda.book.loan.workflow.delegate.Status.FOUND;

@Slf4j
public class OutOfStock implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        boolean isAvailable = (boolean) execution.getVariable(String.valueOf(AVAILABLE).toLowerCase());
        boolean isFound = (boolean) execution.getVariable(String.valueOf(FOUND).toLowerCase());
        if(!isAvailable & isFound){
            log.info("Out Of Stock: {}", execution.getVariable("title"));
        }
        if(!isAvailable && !isFound){
            log.info("Unable to find: {}", execution.getVariable("title"));
        }
    }
}
