package com.jiazhao.ebankspringkafka.pojo;

import java.util.HashMap;

public class Constants {
    public static final String BROKER_LIST = "43.130.156.93:9092,43.130.117.222:9092,43.130.156.73:9092,43.130.156.73:9092";
    public static final double[][] exchangeRates = {{1, 1.14, 1.13}, {0.88, 1, 0.99}, {0.89, 1.01, 1}};
    public static final HashMap<String, Integer> currencyTypeMap;
    static {
        currencyTypeMap = new HashMap<>();
        currencyTypeMap.put("GBP", 0);
        currencyTypeMap.put("EUR", 1);
        currencyTypeMap.put("CHF", 2);
    }
}
