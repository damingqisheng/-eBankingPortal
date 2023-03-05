package com.jiazhao.ebankspringkafka.controller;

import com.jiazhao.ebankspringkafka.pojo.Token;
import com.jiazhao.ebankspringkafka.pojo.User;
import com.jiazhao.ebankspringkafka.service.AuthenticationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private AuthenticationService authenticationService;

    @Autowired
    private AuthenticationController authenticationController;

    @LocalServerPort
    private int port;

    private User testUser;

    @Before
    public void setUp() {
        testUser = new User("admin@gmail.com_EUR", "123", 99);
    }

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetUserById() {

        Token token = authenticationController.authenticateHost(testUser);
        System.out.println(token);
        when(authenticationService.authenticate(testUser)).thenReturn(token);
        ResponseEntity<User> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/authenticate/host",
                User.class
        );
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody(), token);
        verify(authenticationService).authenticate(testUser);
    }
}
