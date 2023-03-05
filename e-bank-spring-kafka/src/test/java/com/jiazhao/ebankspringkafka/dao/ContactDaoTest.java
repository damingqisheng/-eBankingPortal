package com.jiazhao.ebankspringkafka.dao;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.jiazhao.ebankspringkafka.kafka.ProducerFastStart;
import com.jiazhao.ebankspringkafka.pojo.Contact;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ContactDaoTest {

    @MockBean
    private ProducerFastStart producerFastStart;
    @Autowired
    ContactDao contactDao;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddContact() throws Exception {
        contactDao.producerFastStart = producerFastStart;
        Contact contact = new Contact();
        contact.setFollowerId(1);
        contact.setFollower("follower");

        ArgumentCaptor<Integer> integerCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Contact> contactCaptor = ArgumentCaptor.forClass(Contact.class);

        contactDao.addContact(contact);

        verify(producerFastStart).addContact(integerCaptor.capture(), stringCaptor.capture(), contactCaptor.capture());
        Assert.assertEquals(1, java.util.Optional.of(integerCaptor.getValue()));
        Assert.assertEquals("follower", stringCaptor.getValue());
        Assert.assertEquals(contact, contactCaptor.getValue());
    }
}
