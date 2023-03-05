package com.jiazhao.ebankspringkafka.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jiazhao.ebankspringkafka.pojo.Contact;
import com.jiazhao.ebankspringkafka.pojo.Generator;
import com.jiazhao.ebankspringkafka.pojo.Transaction;
import com.jiazhao.ebankspringkafka.pojo.User;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import scala.tools.nsc.backend.icode.analysis.TypeFlowAnalysis;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@SpringBootTest
public class ProducerFastStartTest {
    @Mock
    ProducerFastStart producerFastStart;

    @Test
    public void testRegisterUser() throws JsonProcessingException {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(producerFastStart);
        User user = new User("admin@gmail.com_EUR", "123", "usd", "admin");
        producerFastStart.registerUser("test", user);
    }

    @Test
    @Transactional
    public void testMakeTransaction() throws JsonProcessingException {
        try {
            LocalDateTime datetime = LocalDateTime.of(2023, 1, 2, 0, 0);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedTime = datetime.format(formatter);
            String transactionId = Generator.generateUniqueId();
            Transaction transaction =
                    new Transaction(transactionId, "admin@gmail.com_EUR", 99,
                            "jerry@gmail.com_GBP", 111, -1, formattedTime);

            producerFastStart.makeTransaction(transaction, datetime.toEpochSecond(ZoneOffset.UTC));
            Transaction transaction2 =
                    new Transaction(transactionId, "jerry@gmail.com_GBP", 111,
                            "admin@gmail.com_EUR", 99, +1, formattedTime);
            producerFastStart.makeTransaction(transaction2, datetime.toEpochSecond(ZoneOffset.UTC));
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testAddContact() throws JsonProcessingException {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(producerFastStart);
        User user = new User("admin@gmail.com_EUR", "123", "usd", "admin");
        user.setUserId(99);
        Contact contact = new Contact("user1000", 80, "EUR", user.getUsername(), user.getUserId());
        producerFastStart.addContact(user.getUserId(), user.getUsername(), contact);
    }

}
