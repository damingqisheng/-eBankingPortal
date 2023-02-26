package com.jiazhao.ebankspringkafka.pojo;

import java.util.UUID;

public class Generator {
    public static int generateNumByString(String s) {
        int num = 0;
        for (char c : s.toCharArray()) {
            num += c;
        }
        return num % 50;
    }

    public static String generateUniqueId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
