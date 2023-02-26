package com.jiazhao.ebankspringkafka.service;

import com.jiazhao.ebankspringkafka.Exception.UserNotExistException;
import com.jiazhao.ebankspringkafka.dao.TransactionDao;
import com.jiazhao.ebankspringkafka.dao.UserDao;
import com.jiazhao.ebankspringkafka.pojo.Transaction;
import com.jiazhao.ebankspringkafka.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return transactionDao.getTransactions(id, username, date);
    }
}
