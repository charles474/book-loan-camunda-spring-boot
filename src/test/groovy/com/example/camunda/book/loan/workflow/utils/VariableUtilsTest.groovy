package com.example.camunda.book.loan.workflow.utils

import com.example.camunda.book.loan.workflow.model.Book
import spock.lang.Specification

class VariableUtilsTest extends Specification{

    final VariableUtils variableUtils = new VariableUtils();

    def 'sample book title should equal extracted title'(){
        given:
        def book = new Book("Test Sample", 1);

        when:
        def variables = variableUtils.getVariables(book);

        then:
        variables.get("title") == book.getTitle();
    }
}
