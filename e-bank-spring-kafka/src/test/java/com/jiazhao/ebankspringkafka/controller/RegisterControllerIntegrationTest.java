package com.jiazhao.ebankspringkafka.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiazhao.ebankspringkafka.pojo.User;
import com.jiazhao.ebankspringkafka.service.RegisterService;
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
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegisterControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private RegisterService registerService;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddHost() throws JsonProcessingException {
        User user = new User("admin@gmail.com_EUR", "123", 99);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        ResponseEntity<Integer> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/register/host",
                entity,
                Integer.class
        );

        // Then
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(java.util.Optional.ofNullable(response.getBody()), 200);
        verify(registerService).add(user);
    }

}
