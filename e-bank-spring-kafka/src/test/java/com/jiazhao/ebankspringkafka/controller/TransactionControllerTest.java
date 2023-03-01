package com.jiazhao.ebankspringkafka.controller;

import com.jiazhao.ebankspringkafka.pojo.Transaction;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TransactionControllerTest {
    @Mock
    TransactionController transactionController;

    @Test
    public void getMakeTransaction() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(transactionController);
        Transaction transaction = new Transaction();
        Assert.assertEquals(0, transactionController.makeTransaction(transaction));
    }
}
