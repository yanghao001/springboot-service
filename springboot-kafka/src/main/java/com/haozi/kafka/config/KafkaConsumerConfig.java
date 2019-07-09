package com.haozi.kafka.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String brokers;

    @Value("${spring.kafka.consumer.group-id}")
    private String group;

    @Value("${spring.kafka.consumer.key-deserializer}")
    private String keyType;

    @Value("${spring.kafka.consumer.value-deserializer}")
    private String valueType;
}
