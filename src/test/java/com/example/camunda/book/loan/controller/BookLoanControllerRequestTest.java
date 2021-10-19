package com.example.camunda.book.loan.controller;

import com.example.camunda.book.loan.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class BookLoanControllerRequestTest {

    @Mock
    private BookLoanController controller;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void loanBookShouldReturnStatusCode200() throws Exception {
        // Given
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        Book book = new Book("sample title", 0);

        // Then
        this.mockMvc.perform(post("/v1/loanBook")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk());
    }

}