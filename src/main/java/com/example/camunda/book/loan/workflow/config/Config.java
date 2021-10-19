package com.example.camunda.book.loan.workflow.config;

import com.example.camunda.book.loan.BookLoanProcess;
import com.example.camunda.book.loan.workflow.bpmn.BpmnBookProcessor;
import com.example.camunda.book.loan.workflow.delegate.LoanBook;
import com.example.camunda.book.loan.workflow.delegate.OutOfStock;
import com.example.camunda.book.loan.workflow.delegate.RejectLoan;
import com.example.camunda.book.loan.workflow.delegate.StockChecker;
import com.example.camunda.book.loan.workflow.model.Book;
import com.example.camunda.book.loan.workflow.repository.BookRepository;
import com.example.camunda.book.loan.workflow.utils.VariableUtils;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public BpmnBookProcessor bpmnBookProcessor(RuntimeService runtimeService, VariableUtils variableUtils){
        return new BpmnBookProcessor(runtimeService, variableUtils);
    }

    @Bean
    public VariableUtils getVariableUtils(){
        return new VariableUtils();
    }

    @Bean
    public BookLoanProcess getBookLoanProcess(){
        return new BookLoanProcess();
    }

    @Bean
    public LoanBook loanBook(){
        return new LoanBook();
    }

    @Bean
    public OutOfStock outOfStock(){
        return new OutOfStock();
    }

    @Bean
    public RejectLoan rejectLoan(){
        return new RejectLoan();
    }

    @Bean
    public StockChecker stockChecker(BookRepository bookRepository){
        return new StockChecker(bookRepository);
    }

    @Bean
    public CommandLineRunner commandLineRunner (BookRepository bookRepository){
        // Pre-populate our in-memory DB
        return args -> {
            Book aliceInWonderland= new Book("Alice In Wonderland", 3);
            Book oliverTwist = new Book("Oliver Twist", 2);
            Book aTaleOfTwoCities= new Book("A Tale Of Two Cities", 5);
            bookRepository.save(aliceInWonderland);
            bookRepository.save(oliverTwist);
            bookRepository.save(aTaleOfTwoCities);
        };
    }

}
