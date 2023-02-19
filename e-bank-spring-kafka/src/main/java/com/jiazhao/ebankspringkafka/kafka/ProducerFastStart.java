package com.jiazhao.ebankspringkafka.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerFastStart {

    private static final String brokerList = "192.168.44.130:9092";

    private static final String topic = "transaction";

    public static void main(String[] args) {
        Properties properties = new Properties();
        //设置key序列化器
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //设置重试次数
        properties.put(ProducerConfig.RETRIES_CONFIG, 10);

        //设置值序列化器
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // 设置集群地址
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);

        //设置acks
        properties.put(ProducerConfig.ACKS_CONFIG, "-1");

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, "kafka-demo", "hello, kafka");
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
}
