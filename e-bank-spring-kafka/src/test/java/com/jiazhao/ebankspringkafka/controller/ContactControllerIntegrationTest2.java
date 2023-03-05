package com.jiazhao.ebankspringkafka.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiazhao.ebankspringkafka.dao.ContactDao;
import com.jiazhao.ebankspringkafka.dao.TransactionDao;
import com.jiazhao.ebankspringkafka.dao.UserDao;
import com.jiazhao.ebankspringkafka.kafka.ConsumerFastStart;
import com.jiazhao.ebankspringkafka.kafka.ProducerFastStart;
import com.jiazhao.ebankspringkafka.pojo.Contact;
import com.jiazhao.ebankspringkafka.pojo.User;
import com.jiazhao.ebankspringkafka.service.ContactService;
import com.jiazhao.ebankspringkafka.service.SearchService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ContactControllerIntegrationTest2 {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ConsumerFastStart consumerFastStart;

    @Autowired
    ProducerFastStart producerFastStart;

    @Autowired
    ContactDao contactDao;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addContactTest() throws Exception {
        UserDao userDao = new UserDao(consumerFastStart, producerFastStart);
        User user = new User();
        user.setUsername("test_user");
        user.setPassword("test_password");
        user.setUserId(1);
        userDao.saveUser(user);

        Contact contact = new Contact();
        contact.setFollower(user.getUsername());
        contact.setFollowerId(user.getUserId());
        contact.setUsername("test_followee");
        contact.setUserId(2);

        mockMvc.perform(post("/add/contact")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(contact)))
                .andExpect(status().isOk());

        TransactionDao transactionDao = new TransactionDao(consumerFastStart);
        List<Contact> contacts =  transactionDao.getContacts(user.getUserId(), user.getUsername());
        Assert.assertEquals(1, contacts.size());
        Assert.assertEquals("test_follower", contacts.get(0).getFollower());
    }
}
