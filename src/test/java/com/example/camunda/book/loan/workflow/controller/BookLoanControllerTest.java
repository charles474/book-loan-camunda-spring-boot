package com.example.camunda.book.loan.workflow.controller;

import com.example.camunda.book.loan.BookLoanCamundaSpringBootApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BookLoanCamundaSpringBootApplication.class)
class BookLoanControllerTest {

    @Autowired
    private BookLoanController controller;

    @Test
    public void shouldCheckThatControllerInstanceIsNotNull(){
        assertThat(controller).isNotNull();
    }

}