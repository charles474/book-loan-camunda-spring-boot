package com.example.camunda.book.loan.config;

import com.example.camunda.book.loan.BookLoanProcess;
import com.example.camunda.book.loan.bpmn.BpmnProcessor;
import com.example.camunda.book.loan.delegate.LoanBookDelegate;
import com.example.camunda.book.loan.delegate.StockCheckerDelegate;
import com.example.camunda.book.loan.model.Book;
import com.example.camunda.book.loan.repository.BookRepository;
import com.example.camunda.book.loan.utils.VariableUtils;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public BpmnProcessor getBpmnProcessor(RuntimeService runtimeService, VariableUtils variableUtils){
        return new BpmnProcessor(runtimeService, variableUtils);
    }

    @Bean
    public VariableUtils getVariableUtils(){
        return new VariableUtils();
    }

    @Bean
    public BookLoanProcess getBookLoanProcess(BpmnProcessor bpmnProcessor){
        return new BookLoanProcess(bpmnProcessor);
    }

    @Bean
    public StockCheckerDelegate getStockCheckerDelegate(BookRepository bookRepository){
        return new StockCheckerDelegate(bookRepository);
    }

    @Bean
    public LoanBookDelegate getLoanBookDelegate(){
        return new LoanBookDelegate();
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
