package com.jiazhao.ebankspringkafka.kafka;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;

@SpringBootTest
public class TopicManagementTest {
    @InjectMocks
    TopicManagement topicManagement;

//    @Test
//    public void testCreateTopicConfig() {
//        MockitoAnnotations.initMocks(this);
//        MockitoAnnotations.initMocks(topicManagement);
////        topicManagement.createTopicConfig("test");
//    }

    @Test
    public void testDescribeTopicConfig() throws ExecutionException, InterruptedException {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(topicManagement);
        topicManagement.describeTopicConfig("test");
    }

//    @Test
//    public void testAddTopicPartitions() throws ExecutionException, InterruptedException {
//        MockitoAnnotations.initMocks(this);
//        MockitoAnnotations.initMocks(topicManagement);
//        topicManagement.addTopicPartitions("test");
//    }


}
