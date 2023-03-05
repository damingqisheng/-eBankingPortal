package com.jiazhao.ebankspringkafka.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiazhao.ebankspringkafka.pojo.Contact;
import com.jiazhao.ebankspringkafka.service.ContactService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ContactControllerUnitTest {
    private ContactService contactService = mock(ContactService.class);
    private ContactController contactController = new ContactController(contactService);

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddContact() throws JsonProcessingException {
        Contact contact = new Contact("admin@gmail.com_EUR", 99, "EUR","tom@gmail.com_EUR", 111);
        when(contactService.addContact(contact)).thenReturn(1);

        int result = contactController.addContact(contact);

        Assert.assertEquals(result, 1);
        verify(contactService).addContact(contact);
    }
}
