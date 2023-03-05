package com.jiazhao.ebankspringkafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiazhao.ebankspringkafka.Exception.UserNotExistException;
import com.jiazhao.ebankspringkafka.dao.ContactDao;
import com.jiazhao.ebankspringkafka.dao.UserDao;
import com.jiazhao.ebankspringkafka.pojo.Contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ContactServiceTest {

    @Mock
    private ContactDao contactDao;

    @Mock
    private UserDao userDao;

    @InjectMocks
    private ContactService contactService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testAddContact_UserNotExist() {
        Contact contact = new Contact();
        contact.setUsername("nonexistentuser");
        when(userDao.findByUsername(contact.getUsername())).thenReturn(null);

        assertThrows(UserNotExistException.class, () -> contactService.addContact(contact));
    }


}
