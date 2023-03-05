package com.jiazhao.ebankspringkafka.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiazhao.ebankspringkafka.kafka.ConsumerFastStart;
import com.jiazhao.ebankspringkafka.kafka.ProducerFastStart;
import com.jiazhao.ebankspringkafka.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    @Autowired
    ConsumerFastStart consumerFastStart;

    @Autowired
    ProducerFastStart producerFastStart;

    public UserDao(ConsumerFastStart consumerFastStart, ProducerFastStart producerFastStart) {
        this.producerFastStart = producerFastStart;
        this.consumerFastStart = consumerFastStart;
    }

    public User findByUsername(String username) {
        return consumerFastStart.findUser(username);
    }

    public boolean userExists(User user) {
        return consumerFastStart.userExists("users", user.getUsername());
    }

    public void saveUser(User user) throws JsonProcessingException {
        producerFastStart.registerUser("users", user);
    }

}
