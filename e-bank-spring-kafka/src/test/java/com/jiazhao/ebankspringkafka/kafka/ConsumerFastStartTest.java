package com.jiazhao.ebankspringkafka.kafka;

import com.jiazhao.ebankspringkafka.pojo.User;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConsumerFastStartTest {
    @Mock
    ConsumerFastStart consumerFastStart;

    @Test
    public void testUserExists() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(consumerFastStart);
        User user = new User("admin@gmail.com_EUR", "123", "EUR", "admin");
        System.out.println(consumerFastStart.userExists("test", user.getUsername()));
    }

    @Test
    public void testGetTransactions() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(consumerFastStart);
        User user = new User("admin@gmail.com_EUR", "123", "EUR", "admin");
        System.out.println(consumerFastStart.getTransactions(99,  user.getUsername(), 0, System.currentTimeMillis()));
    }

    @Test
    public void testFindUser() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(consumerFastStart);
        User user = new User("admin@gmail.com_EUR", "123", "EUR", "admin");
        System.out.println(consumerFastStart.findUser(user.getUsername()));
    }

    @Test
    public void testGetContacts() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(consumerFastStart);
        User user = new User("admin@gmail.com_EUR", "123", "EUR", "admin");
        System.out.println(consumerFastStart.getContacts(user.getUserId(), user.getUsername()));
    }

}
