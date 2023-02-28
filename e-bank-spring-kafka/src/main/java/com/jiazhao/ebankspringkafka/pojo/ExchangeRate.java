package com.jiazhao.ebankspringkafka.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRate {
    private double GBP;
    private double EUR;
    private double CHF;
}
