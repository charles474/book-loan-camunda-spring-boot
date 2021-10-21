package com.example.camunda.book.loan.workflow.delegate


import nl.altindag.log.LogCaptor
import org.camunda.bpm.engine.delegate.DelegateExecution
import spock.lang.Specification

import static org.assertj.core.api.AssertionsForClassTypes.assertThat

class RejectLoanTest extends Specification{

    def logCaptor = LogCaptor.forClass(RejectLoan.class);

    def "should log, capture and compare captured logs"(){
        given: "book title"
        def execution = Mock(DelegateExecution);
        def rejectLoan = new RejectLoan();

        when: "invoke the execution method in RejectLoan class"
        rejectLoan.execute(execution);
        def logs = logCaptor.getLogs();

        then:
        assertThat(logs.get(0)).isEqualTo("No book title provided, loan Rejected");
    }

}
