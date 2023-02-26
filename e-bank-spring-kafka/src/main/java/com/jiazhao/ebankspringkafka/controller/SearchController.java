package com.jiazhao.ebankspringkafka.controller;

import com.jiazhao.ebankspringkafka.pojo.Transaction;
import com.jiazhao.ebankspringkafka.pojo.User;
import com.jiazhao.ebankspringkafka.pojo.UserContacts;
import com.jiazhao.ebankspringkafka.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    SearchService searchService;

//    @PostMapping("/userInfo")
//    public List<Transaction> getUserInfo(String username) {
//        User user = searchService.getUserInfo(username);
//        LocalDate currentDate = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
//        String yearMonthString = currentDate.format(formatter);
//        return getTransactions(user.getUserId(), username, yearMonthString);
//    }

    @PostMapping("/transactions")
    public List<Transaction> getTransactions(@RequestParam int userId, @RequestParam String username, @RequestParam String dateString) {
        return searchService.getTransactions(userId, username, dateString);
    }

    @PostMapping("/contacts")
    public UserContacts getContacts() {
        return new UserContacts();
    }

}
