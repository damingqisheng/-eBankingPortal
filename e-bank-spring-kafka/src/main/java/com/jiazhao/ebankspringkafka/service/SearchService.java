package com.jiazhao.ebankspringkafka.service;

import com.jiazhao.ebankspringkafka.Exception.UserNotExistException;
import com.jiazhao.ebankspringkafka.dao.TransactionDao;
import com.jiazhao.ebankspringkafka.dao.UserDao;
import com.jiazhao.ebankspringkafka.pojo.Contact;
import com.jiazhao.ebankspringkafka.pojo.Transaction;
import com.jiazhao.ebankspringkafka.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class SearchService {
    @Autowired
    UserDao userDao;

    @Autowired
    TransactionDao transactionDao;



    public User getUserInfo(String username) {
        User user = userDao.findByUsername(username);
        if(user == null) {
            throw new UserNotExistException("user not exist");
        }
        return user;
    }

    public List<Transaction> getTransactions(int id, String username, String date) {
        LocalDate localDate  = LocalDate.parse(date);
        LocalDate nextMonth = localDate.plusMonths(1);
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        Instant instant = zonedDateTime.toInstant();

        ZonedDateTime zonedDateTime2 = nextMonth.atStartOfDay(ZoneId.systemDefault());
        Instant instant2 = zonedDateTime2.toInstant();
        long startTime = instant.toEpochMilli();
        long endTime = instant2.toEpochMilli();
        List<Transaction> res = transactionDao.getTransactions(id, username, startTime, endTime);
        return res;
    }

    public List<Contact> getContacts(int userId, String username) {
        return transactionDao.getContacts(userId, username);
    }
}
