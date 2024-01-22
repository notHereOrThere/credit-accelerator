package com.example.deal.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Arrays;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${topic.finish-registration}")
    private String finishRegistration;

    @Value("${topic.create-documents}")
    private String createDocument;

    @Value("${topic.send-documents}")
    private String sendDocuments;

    @Value("${topic.send-ses}")
    private String sendSes;

    @Value("${topic.credit-issued}")
    private String creditIssued;

    @Value("${topic.application-denied}")
    private String applicationDenied;

    @Value("${replicas.count}")
    private Integer replicasCount;

    @Value("${partitions.count}")
    private Integer partitionsCount;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaAdmin(configs);
    }

    @Bean
    public AdminClient adminClient() {
        return AdminClient.create(kafkaAdmin().getConfigurationProperties());
    }

    @Bean
    public List<NewTopic> topics() {
        return Arrays.asList(
                new NewTopic(finishRegistration, partitionsCount, replicasCount.shortValue()),
                new NewTopic(createDocument, partitionsCount, replicasCount.shortValue()),
                new NewTopic(sendDocuments, partitionsCount, replicasCount.shortValue()),
                new NewTopic(sendSes, partitionsCount, replicasCount.shortValue()),
                new NewTopic(creditIssued, partitionsCount, replicasCount.shortValue()),
                new NewTopic(applicationDenied, partitionsCount, replicasCount.shortValue())
        );
    }
}