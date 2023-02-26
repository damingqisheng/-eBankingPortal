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

    public List<Transaction> getTransactions(int userId, String username, String date) {
        String topic = "transactions" + userId % 7;
        KafkaConsumer<String, String> consumer = getKafkaConsumer("group.getUser");

        int partition = Generator.generateNumByString(username);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        LocalDate localDate = LocalDate.parse(date + "-01", formatter);
        LocalDate nextMonth = localDate.plusMonths(1);

        Instant startTime = Instant.parse(date + "-" + "01T00:00:00Z");
        Instant endTime = Instant.parse(nextMonth.toString() + "T00:00:00Z");
        TopicPartition topicPartition = new TopicPartition(topic, partition);
        long startOffset = getOffsetByTime(consumer, topicPartition, startTime.toEpochMilli());
        long endOffset = getOffsetByTime(consumer, topicPartition, endTime.toEpochMilli());
        consumer.assign(Arrays.asList(new TopicPartition(topic, partition)));

        consumer.seek(topicPartition, startOffset);
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(10000));

        List<Transaction> transactions = new ArrayList<>();
        try {
            for (ConsumerRecord<String, String> record : records) {
                if (record.offset() >= endOffset) {
                    break;
                }
//                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                if(record.key().equals(username)) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(record.value());
                    String transactionId = jsonNode.get("transactionId").asText();
                    String receiver = jsonNode.get("receiver").asText();
                    int receiverId = jsonNode.get("receiverId").asInt();
                    double transAmount = jsonNode.get("transAmount").asInt();
                    double debit =  jsonNode.get("debit").asDouble();
                    double credit = jsonNode.get("credit").asDouble();
                    Date time = new Date(jsonNode.get("time").asText());
                    Transaction transaction = new Transaction(transactionId, username, userId, receiver, receiverId, transAmount, debit, credit, time);
                    transactions.add(transaction);
                }
            }
            if(transactions.isEmpty()) return null;
            return transactions;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
        return null;
    }

    private static long getOffsetByTime(KafkaConsumer<?, ?> consumer, TopicPartition topicPartition, long timestamp) {
        return consumer.offsetsForTimes(Collections.singletonMap(topicPartition, timestamp))
                .get(topicPartition)
                .offset();
    }

    public User findUser(String username) {
        String topic = "users";
        KafkaConsumer<String, String> consumer = getKafkaConsumer("group.findUser");
        //subscribe topic with a rebalance listener;

        int partition = Generator.generateNumByString(username);
        consumer.assign(Arrays.asList(new TopicPartition(topic, partition)));
        consumer.seekToBeginning(Arrays.asList(new TopicPartition(topic, partition)));
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(10000));
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
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(10000));

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

    public static void main(String[] args) {
        ConsumerFastStart consumerFastStart = new ConsumerFastStart();
        User user = new User("tom@gmail.com_GBP", "123", "GBP", "");
        System.out.println(consumerFastStart.findUser(user.getUsername()));
    }
}
