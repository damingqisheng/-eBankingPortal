package com.jiazhao.ebankspringkafka.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiazhao.ebankspringkafka.pojo.User;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RegisterControllerTest {
    @Mock
    RegisterController registerController;

    @Test
    public void testAddHost() throws JsonProcessingException {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(registerController);
        User user = new User();
        user.setUsername("admin@gmail.com_EUR");
        user.setUserId(99);
        Assert.assertEquals(0, registerController.addHost(user));

    }

}
