package com.example.camunda.book.loan.workflow.bpmn;

import com.example.camunda.book.loan.workflow.model.Book;
import com.example.camunda.book.loan.workflow.utils.VariableUtils;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.variable.VariableMap;

import static com.example.camunda.book.loan.workflow.model.ProcessInstance.LOAN_BOOK_PROCESS_INSTANCE;

public class BpmnBookProcessor {

    private final RuntimeService runtimeService;
    private final VariableUtils variableUtils;

    public BpmnBookProcessor(RuntimeService runtimeService, VariableUtils variableUtils) {
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
