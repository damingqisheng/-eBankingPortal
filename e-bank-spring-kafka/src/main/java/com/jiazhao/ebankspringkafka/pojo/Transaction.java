package com.jiazhao.ebankspringkafka.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Transaction implements Serializable {
    private String transactionId;
    private String username;
    private int userId;
    private String receiver;
    private int receiverId;
    private double transAmount;
    private double debit;
    private double credit;
    private String time;
    private String currencyType;

    public Transaction(String transactionId, String username, int userId, String receiver, int receiverId, double transAmount, double debit, double credit, String time) {
        this.transactionId = transactionId;
        this.username = username;
        this.userId = userId;
        this.receiver = receiver;
        this.receiverId = receiverId;
        this.transAmount = transAmount;
        this.debit = debit;
        this.credit = credit;
        this.time = time;
    }

    public Transaction(String username, int userId, String receiver, int receiverId, double transAmount, String time) {
        this.username = username;
        this.userId = userId;
        this.receiver = receiver;
        this.receiverId = receiverId;
        this.transAmount = transAmount;
        this.time = time;
    }

    public Transaction(String transactionId, String username, int userId, String receiver, int receiverId, int transAmount, String time) {
        this.transactionId = transactionId;
        this.username = username;
        this.userId = userId;
        this.receiver = receiver;
        this.receiverId = receiverId;
        this.transAmount = transAmount;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", username='" + username + '\'' +
                ", userId=" + userId +
                ", receiver='" + receiver + '\'' +
                ", receiverId=" + receiverId +
                ", transAmount=" + transAmount +
                ", debit=" + debit +
                ", credit=" + credit +
                ", time=" + time +
                '}';
    }
}
