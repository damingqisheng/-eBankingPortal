package com.jiazhao.ebankspringkafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiazhao.ebankspringkafka.Exception.UserNotExistException;
import com.jiazhao.ebankspringkafka.dao.ContactDao;
import com.jiazhao.ebankspringkafka.dao.UserDao;
import com.jiazhao.ebankspringkafka.pojo.Contact;
import com.jiazhao.ebankspringkafka.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    @Autowired
    ContactDao contactDao;
    @Autowired
    UserDao userDao;

    public int addContact(Contact contact) throws JsonProcessingException {
        User user = userDao.findByUsername(contact.getUsername());
        if(user == null) {
            throw new UserNotExistException("user not exists: " + contact.getUsername());
        }
        contact.setUserId(user.getUserId());
        contactDao.addContact(contact);
        return user.getUserId();
    }
}
