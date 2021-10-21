package com.example.camunda.book.loan.workflow.delegate

import com.example.camunda.book.loan.workflow.model.Book
import com.example.camunda.book.loan.workflow.repository.BookRepository
import nl.altindag.log.LogCaptor
import org.camunda.bpm.engine.delegate.DelegateExecution
import spock.lang.Specification

import static org.assertj.core.api.AssertionsForClassTypes.assertThat

class ReturnBookTest extends Specification{

    def bookTitle = "Alice In Wonderland";

    def "should log book return successful"(){
        given:
        def bookRepository = Mock(BookRepository);
        def execution = Mock(DelegateExecution);
        def returnBook = new ReturnBook(bookRepository);
        def logCaptor = LogCaptor.forClass(ReturnBook.class);

        and:
        execution.getVariable("title") >> bookTitle;
        bookRepository.findByTitleIgnoreCase(bookTitle) >> Optional.of(new Book(1L, bookTitle, 0));

        when:
        returnBook.execute(execution);
        def logs = logCaptor.getLogs();

        then:
        assertThat(logs.get(0)).isEqualTo(String.format("Book return successful for '%s'", bookTitle));
        assertThat(logs.get(1)).isEqualTo(String.format("Stock count for '%s', %s book(s)", bookTitle, 1));
    }

    def "should log book return unsuccessful"(){
        given:
        def bookRepository = Mock(BookRepository);
        def execution = Mock(DelegateExecution);
        def returnBook = new ReturnBook(bookRepository);
        def logCaptor = LogCaptor.forClass(ReturnBook.class);

        and:
        execution.getVariable("title") >> bookTitle;
        bookRepository.findByTitleIgnoreCase(bookTitle) >> Optional.of(new Book(1L, bookTitle, 10));

        when:
        returnBook.execute(execution);
        def logs = logCaptor.getLogs();

        then:
        assertThat(logs.get(0)).isEqualTo(String.format("Book return unsuccessful for '%s'", bookTitle));
    }
}
