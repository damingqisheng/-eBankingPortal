package com.jiazhao.ebankspringkafka.service;

import com.jiazhao.ebankspringkafka.dao.TransactionDao;
import com.jiazhao.ebankspringkafka.pojo.Constants;
import com.jiazhao.ebankspringkafka.pojo.Generator;
import com.jiazhao.ebankspringkafka.pojo.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class TransactionService {
    @Autowired
    TransactionDao transactionDao;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void makeTransaction(Transaction transaction) {
        String transactionId = Generator.generateUniqueId();
        String senderCurrencyType = transaction.getUsername().split("_")[1];
        String receiverCurrencyType = transaction.getReceiver().split("_")[1];
        double exchangeRate =
                Constants.exchangeRates[Constants.currencyTypeMap.get(senderCurrencyType)][Constants.currencyTypeMap.get(receiverCurrencyType)];
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = now.format(formatter);
        transaction.setTransactionId(transactionId);
        transaction.setTime(formattedTime);
        transactionDao.makeTransaction(transaction);
        Transaction transaction2 = new Transaction(transaction.getReceiver(),
                transaction.getReceiverId(), transaction.getUsername(), transaction.getUserId(), -transaction.getTransAmount() * exchangeRate, formattedTime);
        transaction2.setTransactionId(transactionId);
        transactionDao.makeTransaction(transaction2);
    }
}
