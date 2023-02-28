package com.jiazhao.ebankspringkafka;

import com.jiazhao.ebankspringkafka.pojo.ExchangeRateFetch;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EBankSpringKafkaApplication {
    public static void main(String[] args) {
        Thread exchangeRateFetch = new Thread(new ExchangeRateFetch());
        exchangeRateFetch.start();
        SpringApplication.run(EBankSpringKafkaApplication.class, args);
    }

}
