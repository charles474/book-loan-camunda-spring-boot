package com.example.camunda.book.loan;

import com.example.camunda.book.loan.workflow.model.Book;
import com.example.camunda.book.loan.workflow.repository.BookRepository;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.extension.junit5.test.ProcessEngineExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.camunda.bpm.extension.mockito.CamundaMockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BookLoanCamundaSpringBootApplication.class)
class BookLoanCamundaSpringBootApplicationTest {

    private final String LOAN_BOOK_PROCESS_INSTANCE = "book-loan-process";
    private final String END_ID = "end-loan-request";
    private final String STOCK_CHECKER_DELEGATE_NAME = "stockChecker";

    @RegisterExtension
    public ProcessEngineExtension processEngineExtension = ProcessEngineExtension.builder()
            .configurationResource("test.camunda.cfg.xml")
            .build();

    private final ProcessEngine processEngine = processEngineExtension.getProcessEngine();
    private final RuntimeService runtimeService = processEngine.getRuntimeService();

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp(){
        List<Book> bookList = Arrays.asList(
                new Book("Alice In Wonderland", 1),
                new Book("Oliver Twist", 1),
                new Book("A Tale of Two Cities", 0)
        );
        bookRepository.saveAll(bookList);
    }

    @AfterEach
    public void tearDown() {
        reset();
    }

    @ParameterizedTest
    @MethodSource("autoBookLoans")
    @Deployment(resources = {"book-loan.bpmn"})
    public void shouldAcceptBookLoansAndExecuteProvidedDelegates(String title, String expectedDelegateName, boolean availability){
        // Given
        registerJavaDelegateMock(STOCK_CHECKER_DELEGATE_NAME);
        registerJavaDelegateMock(expectedDelegateName);
        VariableMap variableMap = Variables.createVariables()
                .putValue("title", title)
                .putValue("available", availability);

        // When
        ProcessInstance process = runtimeService.startProcessInstanceByKey(LOAN_BOOK_PROCESS_INSTANCE, variableMap);

        // Then
        assertThat(process).hasNotPassed(END_ID);
        verifyJavaDelegateMock(STOCK_CHECKER_DELEGATE_NAME).executed();
        verifyJavaDelegateMock(expectedDelegateName).executed();
    }


    public static Stream<Arguments> autoBookLoans() {
        return Stream.of(
                Arguments.of("Alice In Wonderland", "loanBook", true),
                Arguments.of("Oliver Twist", "loanBook", true),
                Arguments.of("A Tale of Two Cities", "outOfStock", false)
        );
    }

}
