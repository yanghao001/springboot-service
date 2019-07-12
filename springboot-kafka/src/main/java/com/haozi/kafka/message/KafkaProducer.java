package com.haozi.kafka.message;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * @author hao.yang
 * @date 2019/7/9
 */
@Component
public class KafkaProducer {
    private Logger LOG = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate kafkaTemplate;

    /**
     * singleton instance
     */
    private static KafkaProducer instance = null;

    /**
     * get instance
     * @return instance
     */
    public static KafkaProducer getInstance() {
        if (instance == null) {
            instance = new KafkaProducer();
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    public Future<RecordMetadata> send(String topic, String key, String message) {
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, key, message);
        Future<RecordMetadata> result = kafkaTemplate.send(topic, key, message);
        return result;
    }

}
