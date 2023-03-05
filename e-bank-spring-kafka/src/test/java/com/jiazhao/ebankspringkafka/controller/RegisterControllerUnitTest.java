package com.jiazhao.ebankspringkafka.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiazhao.ebankspringkafka.pojo.User;
import com.jiazhao.ebankspringkafka.service.RegisterService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.verify;

@SpringBootTest
public class RegisterControllerUnitTest {

    @Mock
    private RegisterService registerService;
    private RegisterController registerController = new RegisterController(registerService);


    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddHost() throws JsonProcessingException {
        // Given
        User user = new User("admin@gmail.com_EUR", "123", 99);


        // When
        int result = registerController.addHost(user);

        // Then
        Assert.assertEquals(result, 200);
        verify(registerService).add(user);
    }

}
