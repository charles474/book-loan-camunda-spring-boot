package com.example.camunda.book.loan.delegate;

import com.example.camunda.book.loan.model.Book;
import com.example.camunda.book.loan.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.Map;
import java.util.Optional;

@Slf4j
public class BookStockCheckerDelegate implements JavaDelegate {

    private final BookRepository bookRepository;

    public BookStockCheckerDelegate(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String bookTitle = (String) execution.getVariable("title");
        log.info("Checking stock for Book: {}", bookTitle);
        Optional<Book> bookOptional = bookRepository.findByTitleIgnoreCase(bookTitle);
        boolean result = isBookAvailableToLoan(bookOptional);
        if(result){
            bookOptional.get().setAvailable(0);
            bookRepository.save(bookOptional.get());
        }
        execution.setVariable("available", result);
    }

    private boolean isBookAvailableToLoan(Optional<Book> bookOptional){
        return bookOptional.isPresent() && bookOptional.get().getAvailable() == 1 ? true : false;
    }
}
