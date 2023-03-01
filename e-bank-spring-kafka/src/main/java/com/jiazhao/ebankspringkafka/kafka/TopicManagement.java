package com.jiazhao.ebankspringkafka.kafka;

import com.jiazhao.ebankspringkafka.pojo.Constants;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.config.ConfigResource;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ExecutionException;
@Component
public class TopicManagement {

//    public KafkaAdmin kafkaAdmin() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.BROKER_LIST);
//        return new KafkaAdmin(props);
//    }
//    public AdminClient adminClient (){
//        return AdminClient.create(kafkaAdmin().getConfig());
//    }
//    public NewTopic createTopicConfig(String topic) {
//        return new NewTopic(topic, 300, (short) 4);
//    }

    public void describeTopicConfig(String topic) throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.BROKER_LIST);
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

//    public void addTopicPartitions(String topic) throws ExecutionException, InterruptedException {
//        Properties props = new Properties();
//        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.BROKER_LIST);
//        props.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000);
//        AdminClient client = AdminClient.create(props);
//
//        NewPartitions newPartitions = NewPartitions.increaseTo(4);
//        Map<String, NewPartitions> newPartitionMap = new HashMap<>();
//        newPartitionMap.put(topic, newPartitions);
//        CreatePartitionsResult result = client.createPartitions(newPartitionMap);
//        result.all().get();
//        client.close();
//    }

//    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        TopicManagement topicManagement = new TopicManagement();
////        AdminClient adminClient = topicManagement.adminClient();
////        for(int i = 0; i < 2; i++) {
////            if(i != 8) {
////                adminClient.createTopics(
////                        Arrays.asList(topicManagement.createTopicConfig("contacts"+i)));
////                Thread.sleep(1000);
////            }
////        }
//        topicManagement.describeTopicConfig("contacts0");
//    }

}
