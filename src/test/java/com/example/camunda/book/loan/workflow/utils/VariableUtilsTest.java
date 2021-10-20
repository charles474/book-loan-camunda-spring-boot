package com.example.camunda.book.loan.workflow.utils;

import com.example.camunda.book.loan.workflow.model.Book;
import org.camunda.bpm.engine.variable.VariableMap;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class VariableUtilsTest {

    private final VariableUtils variableUtils = new VariableUtils();

    @Test
    public void shouldReturnAVariableMap(){
        //Given
        Book book = new Book("Test Sample", 1);

        //When
        VariableMap variables = variableUtils.getVariables(book);

        //Then
        assertThat(variables.get("title")).isEqualTo(book.getTitle());
    }

}