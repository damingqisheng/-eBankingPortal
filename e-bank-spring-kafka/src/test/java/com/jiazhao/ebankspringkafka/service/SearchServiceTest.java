package com.jiazhao.ebankspringkafka.service;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SearchServiceTest {
    @Mock
    SearchService searchService;

    @Test
    public void testGetUserInfo() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(searchService);
        try {
            searchService.getUserInfo("admin@gmail.com_EUR");
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testGetTransactions() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(searchService);
        try {
            searchService.getTransactions(99, "admin@gmail.com_EUR", "2023-02-01");
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testGetContacts() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(searchService);
        try {
            searchService.getContacts(99, "admin@gmail.com_EUR");
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
}
