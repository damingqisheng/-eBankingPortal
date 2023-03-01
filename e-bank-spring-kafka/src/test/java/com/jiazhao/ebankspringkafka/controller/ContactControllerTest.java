package com.jiazhao.ebankspringkafka.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiazhao.ebankspringkafka.pojo.Contact;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ContactControllerTest {
    @Mock
    ContactController contactController;

    @Test
    public void testAddContact() throws JsonProcessingException {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(contactController);
        Contact contact = new Contact("admin@gmail.com_EUR", 99, "EUR","tom@gmail.com_EUR", 111);
        contactController.addContact(contact);
    }
}
