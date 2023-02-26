package com.jiazhao.ebankspringkafka.dao;

import com.jiazhao.ebankspringkafka.kafka.ConsumerFastStart;
import com.jiazhao.ebankspringkafka.pojo.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionDao {
    @Autowired
    ConsumerFastStart consumerFastStart;

    public List<Transaction> getTransactions(int userId, String username, String date) {
        return consumerFastStart.getTransactions(userId, username, date);
    }
}
