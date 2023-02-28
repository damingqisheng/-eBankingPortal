package com.jiazhao.ebankspringkafka.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiazhao.ebankspringkafka.Exception.UserNotExistException;
import com.jiazhao.ebankspringkafka.kafka.ConsumerFastStart;
import com.jiazhao.ebankspringkafka.kafka.ProducerFastStart;
import com.jiazhao.ebankspringkafka.pojo.Contact;
import com.jiazhao.ebankspringkafka.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ContactDao {
    @Autowired
    ProducerFastStart producerFastStart;


    public void addContact(Contact contact) throws JsonProcessingException {
        producerFastStart.addContact(contact.getFollowerId(), contact.getFollower(), contact);
    }
}
