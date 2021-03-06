package com.example.camunda.book.loan.workflow.delegate;

import com.example.camunda.book.loan.workflow.model.Book;
import com.example.camunda.book.loan.workflow.repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.example.camunda.book.loan.workflow.delegate.Status.BOOK_NOT_FOUND;

@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StockChecker implements JavaDelegate {

    private final BookRepository bookRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String bookTitle = (String) execution.getVariable("title");
        log.info("Checking stock for Book: {}", bookTitle);
        Optional<Book> bookOptional = bookRepository.findByTitleIgnoreCase(bookTitle);
        if(!bookOptional.isPresent()){
            throw new BpmnError(BOOK_NOT_FOUND.toString());
        }
        execution.setVariable("available", isBookAvailableToLoan(bookOptional));
    }

    private boolean isBookAvailableToLoan(Optional<Book> bookOptional){
        return bookOptional.isPresent() && bookOptional.get().getBookCount() > 0 ? true : false;
    }
}
