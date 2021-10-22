package com.example.camunda.book.loan.workflow.delegate


import nl.altindag.log.LogCaptor
import org.camunda.bpm.engine.delegate.DelegateExecution
import spock.lang.Specification

import static org.assertj.core.api.AssertionsForClassTypes.assertThat

class OutOfStockTest extends Specification{

    def logCaptor = LogCaptor.forClass(OutOfStock.class);

    def "should log, capture and compare captured logs"(){
        given: "book title"
        def bookTitle = "Alice In Wonderland";

        and: "stub execution delegate"
        def execution = Stub(DelegateExecution);
        def outOfStock = new OutOfStock();
        execution.getVariable("title") >> bookTitle;
        execution.getVariable("available") >> false;

        when: "invoke the execution method in OutOfStock class"
        outOfStock.execute(execution);
        def logs = logCaptor.getLogs();

        then:
        assertThat(logs.get(0)).isEqualTo(String.format("Out Of Stock (or Not found): %s", bookTitle));
    }

}
