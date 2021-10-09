package com.example.camunda.book.loan;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan
@EnableJpaRepositories
@EnableProcessApplication
@SpringBootApplication
public class BookLoanCamundaSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookLoanCamundaSpringBootApplication.class, args);
    }

}
