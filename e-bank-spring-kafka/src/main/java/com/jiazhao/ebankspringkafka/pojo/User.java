package com.jiazhao.ebankspringkafka.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private int userId;
    private String currencyType;
    private String fullName;
    private String token;
    private double debitNum;
    private double creditNum;

    public User(String username, String password, String currencyType, String fullName) {
        this.username = username;
        this.password = password;
        this.currencyType = currencyType;
        this.fullName = fullName;
    }
}
