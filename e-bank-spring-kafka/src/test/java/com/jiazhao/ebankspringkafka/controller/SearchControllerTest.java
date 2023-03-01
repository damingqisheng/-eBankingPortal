package com.jiazhao.ebankspringkafka.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

public class SearchControllerTest {
    @Mock
    SearchController searchController;

    @Test
    public void testGetTransactions() throws JsonProcessingException {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(searchController);
        searchController.getTransactions(99, "admin@gmail.com_EUR", "2023-02-01");
    }
    @Test
    public void testGetContacts() throws IOException {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(searchController);
        searchController.getContacts(99, "admin@gmail.com_EUR");
    }

    @Test
    public void testGetExchangeRate() throws IOException {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(searchController);
        searchController.getExchangeRate("EUR");
    }

}
