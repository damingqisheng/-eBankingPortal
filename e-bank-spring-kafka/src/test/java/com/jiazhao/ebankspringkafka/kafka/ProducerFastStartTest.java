package com.jiazhao.ebankspringkafka.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jiazhao.ebankspringkafka.pojo.Generator;
import com.jiazhao.ebankspringkafka.pojo.User;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import scala.tools.nsc.backend.icode.analysis.TypeFlowAnalysis;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class ProducerFastStartTest {
    @Mock
    ProducerFastStart producerFastStart;

    @Test
    public void testRegisterUser() throws JsonProcessingException {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(producerFastStart);
        User user = new User("admin@gmail.com", "123", "usd", "admin");
        producerFastStart.registerUser("test", user);
    }


}
