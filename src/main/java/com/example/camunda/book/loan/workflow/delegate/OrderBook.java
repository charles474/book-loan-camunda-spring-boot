package com.example.camunda.book.loan.workflow.delegate;

import com.example.camunda.book.loan.workflow.model.Book;
import com.example.camunda.book.loan.workflow.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import static com.example.camunda.book.loan.workflow.delegate.BookLimit.MAX_BOOK_LIMIT;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderBook implements JavaDelegate {

    private final BookRepository bookRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String bookTitle = (String) execution.getVariable("title");
        if(bookTitle == null){
            log.info("Unable to order book '{}', title is invalid", (Object) null);
        } else {
            bookRepository.save(new Book(bookTitle, MAX_BOOK_LIMIT.getLimit()));
            log.info("Added book: {} to library", bookTitle);
        }
    }

}
