package com.jiazhao.ebankspringkafka.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Transaction {
    private String transactionId;
    private String username;
    private int userId;
    private String receiver;
    private int receiverID;
    private double transAmount;
    private double debit;
    private double credit;
    private Date time;
}
