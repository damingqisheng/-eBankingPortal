package com.jiazhao.ebankspringkafka.dao;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import com.jiazhao.ebankspringkafka.kafka.ConsumerFastStart;
import com.jiazhao.ebankspringkafka.kafka.ProducerFastStart;
import com.jiazhao.ebankspringkafka.pojo.Contact;
import com.jiazhao.ebankspringkafka.pojo.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransactionDaoTest {

    @Mock
    private ConsumerFastStart consumerFastStart;

    @Mock
    private ProducerFastStart producerFastStart;

    @InjectMocks
    private TransactionDao transactionDao;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetTransactions() {
        // 定义测试用的数据
        int userId = 1;
        String username = "testUser";
        long startTime = System.currentTimeMillis() - 1000;
        long endTime = System.currentTimeMillis();
        List<Transaction> mockRes = new ArrayList<>();
        mockRes.add(new Transaction("testUser", 1, "testUser2", 2, 100.0, "2023-02-05"));
        mockRes.add(new Transaction("testUser2", 2, "testUser", 2, 50.0, "2023-02-05"));

        // 模拟consumerFastStart调用返回结果
        when(consumerFastStart.getTransactions(userId, username, startTime, endTime)).thenReturn(mockRes);

        // 调用待测方法
        List<Transaction> res = transactionDao.getTransactions(userId, username, startTime, endTime);

        // 验证结果
        assertEquals(mockRes.size(), res.size());
        assertEquals(mockRes.get(0).getTransactionId(), res.get(0).getTransactionId());
        assertEquals(mockRes.get(0).getTransAmount(), res.get(0).getTransAmount());
        assertEquals(mockRes.get(1).getTransactionId(), res.get(1).getTransactionId());
        assertEquals(mockRes.get(1).getTransAmount(), res.get(1).getTransAmount());

        // 验证consumerFastStart被调用
        verify(consumerFastStart, times(1)).getTransactions(userId, username, startTime, endTime);
    }

    @Test
    public void testGetContacts() {
        int userId = 1;
        String username = "testUser";
        List<Contact> mockRes = new ArrayList<>();
        mockRes.add(new Contact("testUser2", 2, "EUR", "testUser", 1));
        mockRes.add(new Contact("testUser3", 3, "EUR", "testUser", 1));

        when(consumerFastStart.getContacts(userId, username)).thenReturn(mockRes);

        List<Contact> res = transactionDao.getContacts(userId, username);

        assertEquals(mockRes.size(), res.size());
        assertEquals(mockRes.get(0).getUsername(), res.get(0).getUsername());
        assertEquals(mockRes.get(0).getFollowerId(), res.get(0).getFollowerId());
        assertEquals(mockRes.get(1).getUserId(), res.get(1).getUserId());
        assertEquals(mockRes.get(1).getFollowerId(), res.get(1).getFollowerId());


        verify(consumerFastStart, times(1)).getContacts(userId, username);
    }

}
