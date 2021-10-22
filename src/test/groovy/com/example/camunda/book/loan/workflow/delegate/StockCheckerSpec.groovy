package com.example.camunda.book.loan.workflow.delegate

import com.example.camunda.book.loan.workflow.repository.BookRepository
import nl.altindag.log.LogCaptor
import org.camunda.bpm.engine.delegate.DelegateExecution
import spock.lang.Specification

import static org.assertj.core.api.AssertionsForClassTypes.assertThat

class StockCheckerSpec extends Specification{

    def logCaptor = LogCaptor.forClass(StockChecker.class);

    def "should log, capture and compare captured logs"(){
        given:
        def bookTitle = "Alice In Wonderland";

        and: "stubs of repository & execution delegate"
        def bookRepository = Stub(BookRepository);
        def execution = Stub(DelegateExecution);
        def stockChecker = new StockChecker(bookRepository);
        execution.getVariable("title") >> bookTitle;
        bookRepository.findByTitleIgnoreCase(bookTitle) >> Optional.empty();

        when:
        stockChecker.execute(execution);
        def logs = logCaptor.getLogs();

        then:
        assertThat(logs.get(0)).isEqualTo(String.format("Checking stock for Book: %s", bookTitle));
    }

}
