package com.jiazhao.ebankspringkafka.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jiazhao.ebankspringkafka.pojo.Constants;
import com.jiazhao.ebankspringkafka.pojo.Generator;
import com.jiazhao.ebankspringkafka.pojo.Transaction;
import com.jiazhao.ebankspringkafka.pojo.User;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

@Component
public class ProducerFastStart {
    private KafkaProducer<String, String> getKafkaProducer() {
        Properties properties = new Properties();

        //set key serializer
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //Set idempotence to be true
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");

        //set retry times
        properties.put(ProducerConfig.RETRIES_CONFIG, 10);

        //set value serializer
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // set servers address
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.BROKER_LIST);

        //设置acks
        properties.put(ProducerConfig.ACKS_CONFIG, "all");

        return new KafkaProducer<String, String>(properties);
    }

    public void makeTransaction (Transaction transaction) throws JsonProcessingException {
        KafkaProducer<String, String> producer = getKafkaProducer();
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String transactionJson = mapper.writeValueAsString(transaction);
        String topic = "transactions"+ transaction.getUserId() / 7;
        int partition = transaction.getUserId() % 50;
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, partition, transaction.getUsername(), transactionJson);

        try{
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception e) {
                    if(e == null) {
                        System.out.println("topic:" + metadata.topic());
                        System.out.println("partition:" + metadata.partition());
                        System.out.println("offset:" + metadata.offset());
                    }
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }

    public void registerUser(String topic, User user) throws JsonProcessingException {

        user.setUserId((int)(Math.random() * 350));
        user.setDebitNum(5000);
        user.setCreditNum(5000);
        KafkaProducer<String, String> producer = getKafkaProducer();

        int partition = Generator.generateNumByString(user.getUsername());

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String jsonString = mapper.writeValueAsString(user);

        ProducerRecord<String, String> record = new ProducerRecord<>(topic, partition, user.getUsername(), jsonString);

        try{
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception e) {
                    if(e == null) {
                        System.out.println("topic:" + metadata.topic());
                        System.out.println("partition:" + metadata.partition());
                        System.out.println("offset:" + metadata.offset());
                    }
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
//        User user = new User("tom@gmail.com_GBP", "123", "GBP", "tom");
        ProducerFastStart producerFastStart = new ProducerFastStart();
//        producerFastStart.registerUser("users", user);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String formattedDate = formatter.format(date);
        System.out.println(formattedDate);
        Transaction transaction =
                new Transaction(Generator.generateUniqueId(), "admin@gmail.com_GBP", 99,
                        "tom@gmail.com_EUR", 100, -100, 4900, 5000, date);

        producerFastStart.makeTransaction(transaction);

        Transaction transaction2 =
                new Transaction(Generator.generateUniqueId(), "tom@gmail.com_EUR", 100,
                        "admin@gmail.com_GBP", 99, +100, 5100, 5000, date);
        producerFastStart.makeTransaction(transaction2);
    }



}
