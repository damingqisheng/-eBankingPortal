package com.jiazhao.ebankspringkafka.controller;

import com.jiazhao.ebankspringkafka.pojo.Token;
import com.jiazhao.ebankspringkafka.pojo.User;
import com.jiazhao.ebankspringkafka.service.AuthenticationService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthenticationControllerUnitTest {
    @Mock
    private AuthenticationService authenticationService;
    private AuthenticationController authenticationController = new AuthenticationController(authenticationService);

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAuthenticateHost() {

        User user = new User("admin@gmail.com_EUR", "123", 99);
        Token token = new Token("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBnbWFpbC5jb21fRVVSIiwiZXhwIjoxNjc4MDU3MDA0LCJpYXQiOjE2Nzc5NzA2MDR9.mNirEmjFTCij0YdsQt-Dp9L2s6_7PP2MCnegwbWn2dU");

        when(authenticationService.authenticate(user)).thenReturn(token);

        Token result = authenticationController.authenticateHost(user);

        Assert.assertEquals(result, token);
        verify(authenticationService).authenticate(user);
    }



}
