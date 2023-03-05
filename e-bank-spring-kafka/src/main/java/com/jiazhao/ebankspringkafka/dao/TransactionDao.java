package com.jiazhao.ebankspringkafka.dao;

import com.jiazhao.ebankspringkafka.kafka.ConsumerFastStart;
import com.jiazhao.ebankspringkafka.kafka.ProducerFastStart;
import com.jiazhao.ebankspringkafka.pojo.Contact;
import com.jiazhao.ebankspringkafka.pojo.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import scala.util.Try;

import java.time.LocalDate;
import java.util.List;

@Repository
public class TransactionDao {
    @Autowired
    ConsumerFastStart consumerFastStart;

    @Autowired
    ProducerFastStart producerFastStart;

    public TransactionDao(ConsumerFastStart consumerFastStart) {
        this.consumerFastStart = consumerFastStart;
    }


    public List<Transaction> getTransactions(int userId, String username, long startTime, long endTime) {
        List<Transaction> res = consumerFastStart.getTransactions(userId, username, startTime, endTime);
        return res;
    }

    public List<Contact> getContacts(int userId, String username) {
        return consumerFastStart.getContacts(userId, username);
    }

    public void makeTransaction(Transaction transaction) {
        try {
            producerFastStart.makeTransaction(transaction, System.currentTimeMillis());
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}
