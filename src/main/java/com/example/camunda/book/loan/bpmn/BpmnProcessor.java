package com.example.camunda.book.loan.bpmn;

import com.example.camunda.book.loan.model.Book;
import com.example.camunda.book.loan.utils.VariableUtils;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.variable.VariableMap;

import static com.example.camunda.book.loan.bpmn.ProcessInstance.LOAN_BOOK_PROCESS_INSTANCE;

public class BpmnProcessor {

    private final RuntimeService runtimeService;
    private final VariableUtils variableUtils;

    public BpmnProcessor(RuntimeService runtimeService, VariableUtils variableUtils) {
        this.runtimeService = runtimeService;
        this.variableUtils = variableUtils;
    }

    public void loanBook(Book book){
        VariableMap variableMap = variableUtils.getVariables(book);
        runtimeService.createProcessInstanceByKey(LOAN_BOOK_PROCESS_INSTANCE.getName())
                .setVariables(variableMap)
                .execute();
    }
}
