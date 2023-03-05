package com.jiazhao.ebankspringkafka.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jiazhao.ebankspringkafka.pojo.Transaction;
import com.jiazhao.ebankspringkafka.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TransactionController.class)
@ExtendWith(SpringExtension.class)
public class TransactionControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testMakeTransaction() throws Exception {
        Transaction transaction = new Transaction("", "admin@gmail.com_EUR", 99,
                "jerry@gmail.com_GBP", 111, -1, "2023-02-05");

        mockMvc.perform(post("/make/transaction")
                .content(objectMapper.writeValueAsString(transaction))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(transactionService).makeTransaction(any(Transaction.class));
    }
}
