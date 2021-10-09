package com.example.camunda.book.loan.utils;

import com.example.camunda.book.loan.model.Book;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;

public class VariableUtils {

    public VariableMap getVariables(Book book){
        return Variables.createVariables()
                .putValue("title", book.getTitle());
    }

}
