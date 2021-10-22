package com.example.camunda.book.loan.workflow.controller

import com.example.camunda.book.loan.workflow.model.Book
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class BookRequestControllerRequestSpec extends Specification{

    def controller = Mock(BookRequestController);
    def objectMapper = new ObjectMapper();
    def mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    def "loan book request should return status code 200"(){
        when: "a book instance and mock instance of MockMvc"
        def book = new Book("sample title", 0);

        then:
        mockMvc.perform(get("/v1/loanBook")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk());
    }

    def "return book request should return status code 200"(){
        when: "a book instance and mock instance of MockMvc"
        def book = new Book("sample title", 0);

        then:
        mockMvc.perform(post("/v1/returnBook")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk());
    }
}
