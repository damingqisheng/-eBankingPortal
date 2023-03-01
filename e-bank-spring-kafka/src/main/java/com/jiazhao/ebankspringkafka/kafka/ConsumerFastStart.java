package com.jiazhao.ebankspringkafka.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiazhao.ebankspringkafka.pojo.*;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class ConsumerFastStart {


    private KafkaConsumer<String, String> getKafkaConsumer(String groupId) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.BROKER_LIST);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        return new KafkaConsumer<>(properties);

    }

    public List<Transaction> getTransactions(int userId, String username, long startTime, long endTime) {
        System.out.println("startTime: " + startTime);
        System.out.println("endTime: " + endTime);

        String topic = "transactions" + userId % 7;
        KafkaConsumer<String, String> consumer = getKafkaConsumer("group.getUser");

        int partition = userId % 50;
        consumer.assign(Arrays.asList(new TopicPartition(topic, partition)));
        consumer.seekToBeginning(Arrays.asList(new TopicPartition(topic, partition)));

        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
        double totalMoney = 10000;
        List<Transaction> transactions = new ArrayList<>();
        try {
            for (ConsumerRecord<String, String> record : records) {
                if (record.key().equals(username)) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(record.value());
                    double transAmount = jsonNode.get("transAmount").asInt();
                    totalMoney += transAmount;
                    if (record.timestamp() >= startTime && record.timestamp() < endTime) {
                        String transactionId = jsonNode.get("transactionId").asText();
                        String receiver = jsonNode.get("receiver").asText();
                        int receiverId = 0;
                        if (jsonNode.get("receiverID") != null) {
                            receiverId = jsonNode.get("receiverID").asInt();
                        } else {
                            receiverId = jsonNode.get("receiverId").asInt();
                        }
                        double debit = jsonNode.get("debit").asDouble();
                        double credit = jsonNode.get("credit").asDouble();
                        String time = jsonNode.get("time").asText();
                        Transaction transaction = new Transaction(transactionId, username, userId, receiver, receiverId, transAmount, debit, credit, time);
                        transactions.add(transaction);
                        System.out.println(transaction);

                    }
                }
            }
            if(transactions.isEmpty()) return null;
            double balance = totalMoney > 5000 ? totalMoney - 5000 : 0;
            double credit = totalMoney - balance;
            Transaction transaction = new Transaction();
            transaction.setDebit(balance);
            transaction.setCredit(credit);
            transactions.add(transaction);
            return transactions;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
        return null;
    }

    public User findUser(String username) {
        String topic = "users";
        KafkaConsumer<String, String> consumer = getKafkaConsumer("group.findUser");
        //subscribe topic with a rebalance listener;

        int partition = Generator.generateNumByString(username);
        consumer.assign(Arrays.asList(new TopicPartition(topic, partition)));
        consumer.seekToBeginning(Arrays.asList(new TopicPartition(topic, partition)));
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
        try {

            for(ConsumerRecord<String, String> record: records) {
                if(record.key().equals(username)) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(record.value());
                    String password = jsonNode.get("password").asText();
                    int userId = jsonNode.get("userId").asInt();
                    String fullName = jsonNode.get("fullName").asText();
                    String currencyType = jsonNode.get("currencyType").asText();
                    User user = new User(username, password, userId, currencyType, fullName, "", 5000.0f, 5000.0f);
                    System.out.println("username: " +username);
                    System.out.println("password: " +password);
                    System.out.println("currencyType: " +currencyType);
                    return user;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
        return null;
    }

    public boolean userExists(String topic, String username) {
        KafkaConsumer<String, String> consumer = getKafkaConsumer("group.findUser");
        int partition = Generator.generateNumByString(username);
        consumer.assign(Arrays.asList(new TopicPartition(topic, partition)));
        consumer.seekToBeginning(Arrays.asList(new TopicPartition(topic, partition)));
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

        try {
            for(ConsumerRecord<String, String> record: records) {
                if(record.key().equals(username)) {
                    System.out.println("Username: " + username);
                    return true;
                }
            }

        } finally {
            consumer.close();
        }
        return false;

    }

    public List<Contact> getContacts(int userId, String username) {
        String topic = "contacts" + userId % 2;
        int partition = userId % 50;

        KafkaConsumer<String, String> consumer = getKafkaConsumer("group.getContact");

        consumer.assign(Arrays.asList(new TopicPartition(topic, partition)));
        consumer.seekToBeginning(Arrays.asList(new TopicPartition(topic, partition)));

        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
        List<Contact> contacts = new ArrayList<>();
        try {
            for (ConsumerRecord<String, String> record : records) {
                if (record.key().equals(username)) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(record.value());
                    String contactName = jsonNode.get("username").asText();
                    int contactId = jsonNode.get("userId").asInt();
                    Contact contact = new Contact(contactName, contactId);
//                    System.out.println(contact);
                    contacts.add(contact);
                }
            }
            if(contacts.isEmpty()) return null;
            return contacts;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
        return null;
    }

//    public static void main(String[] args) {
//        ConsumerFastStart consumerFastStart = new ConsumerFastStart();
////        User user = new User("admin@gmail.com_EUR", "123", "EUR", "");
////        System.out.println(consumerFastStart.findUser(user.getUsername()));
////        User user = new User("jerry@gmail.com_GBP", "123", "GBP", "");
////        user.setUserId(111);
////        consumerFastStart.getTransactions(user.getUserId(), user.getUsername(), System.currentTimeMillis() - (30L * 24L * 60L * 60L * 1000L) , System.currentTimeMillis());
////        System.out.println("现在是：" + System.currentTimeMillis());
//
//        consumerFastStart.getTransactions(100, "tom@gmail.com_GBP", 0, System.currentTimeMillis());
//    }


}
