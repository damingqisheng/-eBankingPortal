package com.jiazhao.ebankspringkafka.kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;


public class ConsumerFastStart {

    private static final String brokerList = "192.168.44.130:9092";

    private static final String topic = "transaction";

    private static final String groupId = "group.demo";

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();
        //subscribe topic with a rebalance listener;
        consumer.subscribe(Collections.singletonList(topic), new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                consumer.commitSync(currentOffsets);
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> collection) {

            }
        });
//        consumer.assign(Arrays.asList(new TopicPartition(topic, 0)));

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                for(ConsumerRecord<String, String> record: records) {
                    System.out.println(record.value());

                    currentOffsets.put(new TopicPartition(record.topic(), record.partition()),
                            new OffsetAndMetadata(record.offset() + 1));
                }
                consumer.commitSync(currentOffsets);
            }
        } finally {
            consumer.close();
        }

    }
}
