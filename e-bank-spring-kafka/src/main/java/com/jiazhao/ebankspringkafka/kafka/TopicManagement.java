package com.jiazhao.ebankspringkafka.kafka;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.config.ConfigResource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class TopicManagement {
    private static final String brokerList = "192.168.44.130:9092";

    private static final String topic = "transaction";

    public static void describeTopicConfig() throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        props.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000);
        AdminClient client = AdminClient.create(props);

        ConfigResource resource =
                new ConfigResource(ConfigResource.Type.TOPIC, topic);
        DescribeConfigsResult result =
                client.describeConfigs(Collections.singletonList(resource));
        Config config = result.all().get().get(resource);
        System.out.println(config);
        client.close();
    }

    public static void addTopicPartitions() throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        props.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000);
        AdminClient client = AdminClient.create(props);

        NewPartitions newPartitions = NewPartitions.increaseTo(4);
        Map<String, NewPartitions> newPartitionMap = new HashMap<>();
        newPartitionMap.put(topic, newPartitions);
        CreatePartitionsResult result = client.createPartitions(newPartitionMap);
        result.all().get();
        client.close();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        describeTopicConfig();
    }
}
