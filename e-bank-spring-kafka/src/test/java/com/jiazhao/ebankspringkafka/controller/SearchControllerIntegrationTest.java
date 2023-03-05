package com.jiazhao.ebankspringkafka.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.jiazhao.ebankspringkafka.pojo.*;
import com.jiazhao.ebankspringkafka.service.SearchService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SearchController.class)
public class SearchControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchService searchService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    private User user;
    private Transaction transaction;
    private Contact contact;

    @BeforeEach
    public void setUp() {
        user = new User("admin@gmail.com_EUR", "123", 99);

        LocalDateTime datetime = LocalDateTime.of(2023, 1, 2, 0, 0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = datetime.format(formatter);
        String transactionId = Generator.generateUniqueId();
        transaction = new Transaction(transactionId, "admin@gmail.com_EUR", 99,
                        "jerry@gmail.com_GBP", 111, -1, formattedTime);

        contact = new Contact("user1000", 80, "EUR", user.getUsername(), user.getUserId());
        ;
    }

    @Test
    public void testGetUserInfo() throws Exception {
        when(searchService.getUserInfo(user.getUsername())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/search/userInfo/" + user.getUsername()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(equalTo(user.getUserId())));
    }

    @Test
    public void testGetTransactions() throws Exception {
        when(searchService.getTransactions(user.getUserId(), user.getUsername(), "2023-02-01"))
                .thenReturn(Arrays.asList(transaction));

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/search/transactions/"+ user.getUserId() +"/" + user.getUsername() + "/2023-02-01"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String expectedJson = new ObjectMapper().writeValueAsString(Arrays.asList(transaction));
        String actualJson = result.getResponse().getContentAsString();

        Assert.assertEquals(actualJson, expectedJson);
    }

    @Test
    public void testGetContacts() throws Exception {
        when(searchService.getContacts(user.getUserId(), user.getUsername())).thenReturn(Arrays.asList(contact));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/search/contacts/"+user.getUserId()+"/"+user.getUsername()))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String expectedJson = new ObjectMapper().writeValueAsString(Arrays.asList(contact));
        String actualJson = result.getResponse().getContentAsString();

        Assert.assertEquals(actualJson, expectedJson);
    }

    @Test
    public void testGetExchangeRate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/search/exchangeRate/GBP"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(
                        equalTo("{\"GBP\":1,\"EUR\":"+ Constants.exchangeRates[0][1] + ",\"CHF\":"+Constants.exchangeRates[0][2]+"}")));
    }
}
