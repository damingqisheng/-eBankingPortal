package com.jiazhao.ebankspringkafka.service;

import com.jiazhao.ebankspringkafka.dao.TransactionDao;
import com.jiazhao.ebankspringkafka.pojo.Transaction;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TransactionServiceTest {

    @Mock
    private TransactionDao transactionDaoMock;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    public void makeTransaction_shouldSaveTwoTransactions() {
        String sender = "user1_USD";
        String receiver = "user2_EUR";
        int senderId = 1;
        int receiverId = 2;
        double amount = 100.0;

        Transaction transaction = new Transaction(sender, senderId, receiver, receiverId, amount, "2023-02-02");
        transactionService.makeTransaction(transaction);

        // verify that the dao's makeTransaction method was called twice
        verify(transactionDaoMock, times(2)).makeTransaction(any(Transaction.class));
    }

    @Test
    public void makeTransaction_shouldSaveCorrectTransaction() {
        String sender = "user1_USD";
        String receiver = "user2_EUR";
        int senderId = 1;
        int receiverId = 2;
        double amount = 100.0;

        Transaction transaction = new Transaction(sender, senderId, receiver, receiverId, amount, "2023-02-03");
        transactionService.makeTransaction(transaction);

        // capture the arguments of the first call to makeTransaction
        ArgumentCaptor<Transaction> transactionArgumentCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionDaoMock, times(2)).makeTransaction(transactionArgumentCaptor.capture());

        // assert that the captured transaction matches the expected values
        Transaction savedTransaction = transactionArgumentCaptor.getAllValues().get(0);
        Assert.assertEquals(sender, savedTransaction.getUsername());
        Assert.assertEquals(senderId, savedTransaction.getUserId());
        Assert.assertEquals(-amount, savedTransaction.getTransAmount(), 0.001);
        Assert.assertEquals(receiver, savedTransaction.getReceiver());
        Assert.assertEquals(receiverId, savedTransaction.getReceiverId());

        // assert that the second transaction matches the expected values
        Transaction savedTransaction2 = transactionArgumentCaptor.getAllValues().get(1);
        Assert.assertEquals(receiver, savedTransaction2.getUsername());
        Assert.assertEquals(receiverId, savedTransaction2.getUserId());
        Assert.assertEquals(amount, savedTransaction2.getTransAmount(), 0.001);
        Assert.assertEquals(sender, savedTransaction2.getReceiver());
        Assert.assertEquals(senderId, savedTransaction2.getReceiverId());
    }

}
