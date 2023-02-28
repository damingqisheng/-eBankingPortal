package com.jiazhao.ebankspringkafka.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Contact {
    private String username;
    private int userId;
    private String currencyType;
    private String follower;
    private int followerId;


    public Contact(String username, int userId) {
        this.username = username;
        this.userId = userId;
    }
}
