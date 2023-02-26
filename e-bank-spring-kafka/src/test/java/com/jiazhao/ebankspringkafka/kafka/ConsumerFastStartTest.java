package com.jiazhao.ebankspringkafka.kafka;

import com.jiazhao.ebankspringkafka.pojo.User;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ConsumerFastStartTest {
    @Mock
    ConsumerFastStart consumerFastStart;

    @Test
    public void testFindUser() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(consumerFastStart);
        User user = new User("admin@gmail.com", "123", "usd", "admin");
        System.out.println(consumerFastStart.userExists("test", user.getUsername()));
    }
}
