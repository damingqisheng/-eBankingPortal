package com.jiazhao.ebankspringkafka.controller;

import com.jiazhao.ebankspringkafka.pojo.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.client.TestRestTemplate.HttpClientOption;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void makeTransaction_whenValidInput_thenReturns200() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Transaction transaction = new Transaction("", "admin@gmail.com_EUR", 99,
                "jerry@gmail.com_GBP", 111, -1, "2023-02-05");
        HttpEntity<Transaction> request = new HttpEntity<>(transaction, headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                "http://localhost:" + port + "/make/transaction",
                HttpMethod.POST,
                request,
                Void.class
        );

        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }

}