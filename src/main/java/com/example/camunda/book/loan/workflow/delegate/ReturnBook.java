package com.example.camunda.book.loan.workflow.delegate;

import com.example.camunda.book.loan.workflow.model.Book;
import com.example.camunda.book.loan.workflow.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.example.camunda.book.loan.workflow.delegate.BookLimit.MAX_BOOK_LIMIT;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReturnBook implements JavaDelegate {

    private final BookRepository bookRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String bookTitle = (String) execution.getVariable("title");
        Optional<Book> bookOptional = bookRepository.findByTitleIgnoreCase(bookTitle);
        if(!bookOptional.isPresent()){
            log.info("Book '{}' does not belong to this library", bookTitle);
            return;
        }
        if(bookOptional.get().getBookCount() < MAX_BOOK_LIMIT.getLimit()){
            bookOptional.get().setBookCount(bookOptional.get().getBookCount()+1);
            bookRepository.save(bookOptional.get());
            log.info("Book return successful for '{}'", bookTitle);
            log.info("Stock count for '{}', {} book(s)", bookTitle, bookOptional.get().getBookCount());
        } else {
            log.info("Book return unsuccessful for '{}'", bookTitle);
        }
    }

}
