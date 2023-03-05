package com.jiazhao.ebankspringkafka.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiazhao.ebankspringkafka.pojo.Contact;
import com.jiazhao.ebankspringkafka.service.ContactService;
import org.junit.Assert;
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
public class ContactControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private ContactService contactService;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddContact() throws JsonProcessingException {

        Contact contact = new Contact("admin@gmail.com_EUR", 99, "EUR","tom@gmail.com_EUR", 111);

        when(contactService.addContact(contact)).thenReturn(1);

        // When
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Contact> entity = new HttpEntity<>(contact, headers);
        ResponseEntity<Integer> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/add/contact",
                entity,
                Integer.class
        );

        // Then
        Assert.assertEquals (response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals (java.util.Optional.ofNullable(response.getBody()), 1);
        verify(contactService).addContact(contact);
    }

}
