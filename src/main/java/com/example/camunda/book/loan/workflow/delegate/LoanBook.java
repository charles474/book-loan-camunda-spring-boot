package com.example.camunda.book.loan.workflow.delegate;

import com.example.camunda.book.loan.workflow.model.Book;
import com.example.camunda.book.loan.workflow.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.example.camunda.book.loan.workflow.delegate.Status.AVAILABLE;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoanBook implements JavaDelegate {

    private final BookRepository bookRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        boolean isAvailable = (boolean) execution.getVariable(String.valueOf(AVAILABLE).toLowerCase());
        String bookTitle = (String) execution.getVariable("title");
        if(isAvailable){
            log.info("Loan Accepted: {}", bookTitle);
            Optional<Book> bookOptional = bookRepository.findByTitleIgnoreCase(bookTitle);
            execution.setVariable("stockCount", bookOptional.get().getBookCount());
            log.info("Book: {}, Stock book count: {}", bookTitle, bookOptional.get().getBookCount());
            bookOptional.get().setBookCount(bookOptional.get().getBookCount()-1);
            bookRepository.save(bookOptional.get());
            log.info("Book: {}, Remaining count: {}", bookTitle, bookOptional.get().getBookCount());
        }
    }
}
