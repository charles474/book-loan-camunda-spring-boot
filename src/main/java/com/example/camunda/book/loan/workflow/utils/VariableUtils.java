package com.example.camunda.book.loan.workflow.utils;

import com.example.camunda.book.loan.workflow.model.Book;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;

public class VariableUtils {

    public VariableMap getVariables(Book book){
        return Variables.createVariables()
                .putValue("title", book.getTitle());
    }

}
