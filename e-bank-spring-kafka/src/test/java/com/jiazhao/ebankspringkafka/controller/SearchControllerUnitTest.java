package com.jiazhao.ebankspringkafka.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiazhao.ebankspringkafka.pojo.Contact;
import com.jiazhao.ebankspringkafka.pojo.Transaction;
import com.jiazhao.ebankspringkafka.pojo.User;
import com.jiazhao.ebankspringkafka.service.SearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SearchController.class)
@SpringBootTest
public class SearchControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchService searchService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    User user = new User("admin@gmail.com_EUR", "123", 99);

    @Test
    public void testGetUserInfo() throws Exception {
        when(searchService.getUserInfo(user.getUsername())).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.get("/search/userInfo/{username}", "username"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").value(1));
    }

    @Test
    public void testGetTransactions() throws Exception {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction());
        when(searchService.getTransactions(user.getUserId(), user.getUsername(), "2023-02-01")).thenReturn(transactions);
        mockMvc.perform(MockMvcRequestBuilders.get("/search/transactions/{userId}/{username}/{dateString}", 1, "username", "dateString"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testGetContacts() throws Exception {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact());
        when(searchService.getContacts(user.getUserId(), user.getUsername())).thenReturn(contacts);
        mockMvc.perform(MockMvcRequestBuilders.get("/search/contacts/{userId}/{username}", user.getUserId(), user.getUsername()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testGetExchangeRate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/search/exchangeRate/USD"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.GBP").isNumber())
                .andExpect(jsonPath("$.EUR").isNumber())
                .andExpect(jsonPath("$.CHF").isNumber());
    }
}
