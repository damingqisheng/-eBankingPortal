package com.jiazhao.ebankspringkafka.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiazhao.ebankspringkafka.pojo.*;
import com.jiazhao.ebankspringkafka.service.SearchService;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    SearchService searchService;

    @GetMapping("/userInfo/{username}")
    public int getUserInfo(@PathVariable String username) {
        User user = searchService.getUserInfo(username);
       return user.getUserId();
    }

    @GetMapping("/transactions/{userId}/{username}/{dateString}")
    public String getTransactions(@PathVariable int userId, @PathVariable String username, @PathVariable String dateString) throws IOException {
        List<Transaction> transactions = searchService.getTransactions(userId, username, dateString);
        if(transactions == null || transactions.size() == 0)  throw new IOException("No transactions exists");
        Collections.reverse(transactions);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(transactions);
        return json;
    }

    @GetMapping("/contacts/{userId}/{username}")
    public String getContacts(@PathVariable int userId, @PathVariable String username) throws IOException {
        List<Contact> contacts = searchService.getContacts(userId, username);
        if(contacts == null) throw new IOException("No contacts exist");
        Collections.reverse(contacts);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(contacts);
        return json;
    }

    @RequestMapping("/exchangeRate/{currencyType}")
    public String getExchangeRate(@PathVariable String currencyType) throws JsonProcessingException {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setGBP(Constants.exchangeRates[Constants.currencyTypeMap.get(currencyType)][0]);
        exchangeRate.setEUR(Constants.exchangeRates[Constants.currencyTypeMap.get(currencyType)][1]);
        exchangeRate.setCHF(Constants.exchangeRates[Constants.currencyTypeMap.get(currencyType)][2]);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(exchangeRate);
        return json;
    }

}
