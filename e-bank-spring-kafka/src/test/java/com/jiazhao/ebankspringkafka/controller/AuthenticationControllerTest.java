package com.jiazhao.ebankspringkafka.controller;

import com.jiazhao.ebankspringkafka.pojo.User;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;


public class AuthenticationControllerTest {
    @Mock
    AuthenticationController authenticationController;

    @Test
    public void testAuthenticateHost() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(authenticationController);
        User user = new User();
        user.setUsername("admin@gmail.com_EUR");
        user.setUserId(99);
        authenticationController.authenticateHost(user);
    }



}
