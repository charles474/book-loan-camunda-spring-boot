package com.example.camunda.book.loan.workflow.bpmn

import io.digitalstate.camunda.unittest.UnitTestingHelpers
import org.camunda.bpm.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration
import org.camunda.bpm.engine.runtime.ProcessInstance
import org.camunda.bpm.engine.test.ProcessEngineRule
import org.camunda.bpm.engine.variable.VariableMap
import org.camunda.bpm.engine.variable.Variables
import org.junit.ClassRule
import spock.lang.Shared
import spock.lang.Specification

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.runtimeService
import static org.assertj.core.api.AssertionsForClassTypes.assertThat
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.repositoryService
import static org.camunda.bpm.extension.mockito.CamundaMockito.registerJavaDelegateMock
import static org.camunda.bpm.extension.mockito.CamundaMockito.verifyJavaDelegateMock

class BpmnBookProcessorSpec extends Specification implements UnitTestingHelpers, BpmnFluentBuilder{

    final String bookLoanProcessInstance = "book-loan-process";
    final String bookReturnProcessInstance = "book-return-process";
    final String stockCheckerDelegate = "stockChecker";

    @ClassRule
    @Shared ProcessEngineRule processEngineRule = new ProcessEngineRule(new StandaloneInMemProcessEngineConfiguration().buildProcessEngine());


    def setupSpec(){
        setDeploymentFiles([
                'book-loan.bpmn' : fetchModelAsResource("book-loan.bpmn"),
                'book-return.bpmn' : fetchModelAsResource("book-return.bpmn")
        ])
        deployNow(setupDeployment());
    }

    def cleanupSpec(){
        def deploymentId = getSharedData("deploymentId")
        repositoryService().deleteDeployment(deploymentId,true, true, true);
        println "Deployment ID: '${deploymentId}' has been deleted"
    }

    def "should test book-loan.bpmn deployed, ended and delegates invoked"(String title, String delegate, boolean isAvailable, Integer stockCount, boolean approveRequest, boolean expectedStage){
        given: "variables for process instance"
            VariableMap variables = Variables.createVariables()
                .putValue("title", title)
                .putValue("available", isAvailable)
                .putValue("stockCount",stockCount)
                .putValue("approveBookRequest",approveRequest);

        and: "register mocks"
            registerJavaDelegateMock(stockCheckerDelegate);
            registerJavaDelegateMock(delegate);

        when: "creating an instance of book-loan.bpmn"
            ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(bookLoanProcessInstance, variables);

        then: "process has ended, delegates executed"
            assertThat( processInstance.isEnded() == expectedStage);
            verifyJavaDelegateMock(stockCheckerDelegate).executed();
            verifyJavaDelegateMock(delegate).executed();

        where:
            title | delegate | isAvailable | stockCount | approveRequest | expectedStage
            "Alice In Wonderland" | "loanBook" | true | 3 | true | false
            "Oliver Twist" | "loanBook" | true | 3 | true | false
            "A Tale of Two Cities" | "outOfStock" | false | 0 | _ | true

    }

    def "should check that book-loan.bpmn deployed, ended, was rejected and delegates invoked"(String title, String delegate, boolean isAvailable){
        given: "variables for process instance"
            def rejectLoanDelegate = "rejectLoan";
            def variables = Variables.createVariables()
                .putValue("title", "");

        and: "register mocks"
            registerJavaDelegateMock(stockCheckerDelegate);
            registerJavaDelegateMock(rejectLoanDelegate);

        when: "creating an instance of book-loan.bpmn"
            ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(bookLoanProcessInstance, variables);

        then:
            assertThat(processInstance.isEnded() == true);
            verifyJavaDelegateMock(stockCheckerDelegate).executedNever();
            verifyJavaDelegateMock(rejectLoanDelegate).executed();

        where:
            title | delegate | isAvailable
            "Alice In Wonderland" | "loanBook" | true
            "Oliver Twist" | "loanBook" | true
            "A Tale of Two Cities" | "outOfStock" | false
    }
/*
    def "should check book-return.bpmn deployed, and is not ended"(String title){
        given: "variables for process instance"
            VariableMap variables = Variables.createVariables()
                .putValue("title", title);

        when: "creating an instance of book-return.bpmn"
            ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(bookReturnProcessInstance, variables);

        then: "process has ended, delegates executed"
            assertThat(processInstance).isActive();

        where:
            title | _
            "Alice In Wonderland" | _
            "Oliver Twist" | _
            "A Tale of Two Cities" | _

    }

    def "should wait at approve book request manual task"(){
        given: "instance variables"
            VariableMap variables = Variables.createVariables()
                .putValue("title","Alice In Wonderland")
                .putValue("available",true)
                .putValue("stockCount",3);

        and: "register mocks"
            registerJavaDelegateMock(stockCheckerDelegate);
            registerJavaDelegateMock("loanBook");

        when: "create an instance of book-loan.bpmn with the variables"
            ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(bookLoanProcessInstance, variables);

        then: "process is waiting at manual user approve request"
            assertThat(processInstance).isWaitingAt("approve-book-request")

    }
    */
}
