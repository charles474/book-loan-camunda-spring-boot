package com.example.camunda.book.loan.workflow.delegate

import com.example.camunda.book.loan.workflow.model.Book
import com.example.camunda.book.loan.workflow.repository.BookRepository
import nl.altindag.log.LogCaptor
import org.camunda.bpm.engine.delegate.DelegateExecution
import spock.lang.Specification

import static org.assertj.core.api.AssertionsForClassTypes.assertThat

class LoanBookTest extends Specification{

    def logCaptor = LogCaptor.forClass(LoanBook.class);

    def "should log, capture and compare captured logs"(){
        given: "book titles"
        def bookTitle = "Alice In Wonderland";

        and: "stub book repo and execution delegate"
        def bookRepository = Stub(BookRepository);
        def execution = Stub(DelegateExecution);
        def loanBook = new LoanBook(bookRepository);
        bookRepository.findByTitleIgnoreCase(bookTitle) >> Optional.of(new Book(1L, bookTitle, 3));
        execution.getVariable("title") >> bookTitle;
        execution.getVariable("available") >> true;

        when: "invoke the execution method in LoanBook class"
        loanBook.execute(execution);
        def logs = logCaptor.getLogs();

        then:
        assertThat(logs.get(0)).isEqualTo(String.format("Loan Accepted: %s", bookTitle));
        assertThat(logs.get(1)).contains(String.format("Book: %s, Stock book count:", bookTitle));
        assertThat(logs.get(2)).contains(String.format("Book: %s, New remaining count:", bookTitle));
    }

}
