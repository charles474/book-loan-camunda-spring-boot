package com.example.camunda.book.loan;

import com.example.camunda.book.loan.delegate.StockCheckerDelegate;
import com.example.camunda.book.loan.model.Book;
import com.example.camunda.book.loan.repository.BookRepository;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstanceWithVariables;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.extension.junit5.test.ProcessEngineExtension;
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

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BookLoanCamundaSpringBootApplication.class)
class BookLoanCamundaSpringBootApplicationTest {

    private final String LOAN_BOOK_PROCESS_INSTANCE = "book-loan-process";

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
                new Book("Oliver Twist", 1)
        );
        bookRepository.saveAll(bookList);
        Mocks.register("stockCheckerDelegate", new StockCheckerDelegate(bookRepository));
    }

    @ParameterizedTest
    @MethodSource("bookToLoans")
    @Deployment(resources = {"book-loan.bpmn"})
    public void shouldAcceptBookLoans(String title){
        // Given
        VariableMap variableMap = buildVariables(title);

        // When
        ProcessInstanceWithVariables process = runtimeService.createProcessInstanceByKey(LOAN_BOOK_PROCESS_INSTANCE)
                .setVariables(variableMap)
                .executeWithVariablesInReturn();

        //TODO: creation of StockCheckerDelegate instance throws exception.

        // Then
        assertThat(process).isEnded();
    }

    private static Stream<Arguments> bookToLoans(){
        return Stream.of(
                Arguments.of("Alice In Wonderland"),
                Arguments.of("Oliver Twist")
        );
    }

    private VariableMap buildVariables(String title){
        return Variables.createVariables()
                .putValue("title", title);
    }

}
