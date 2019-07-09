package com.haozi.kafka.message;

import com.haozi.kafka.utils.JsonUtil;
import com.haozi.kafka.utils.JsonValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author hao.yang
 * @date 2019/7/9
 */
@Slf4j
@Component
public class KafkaConsumer {

    @KafkaListener(topics = "D2S-REQ")
    public void processMessage(ConsumerRecord<String, String> consumer) {
        try {
            String topic = "";
            String key = "";
            String message = "";
            if (consumer.topic() != null) {
                topic = consumer.topic();
            }
            if (consumer.key() != null) {
                key = consumer.key();
            }
            if (consumer.value() != null) {
                message = consumer.value();
            }

            if (topic == null || "".equals(topic.trim()) || " ".equals(topic)) {
                log.warn("[processMessage] invalid topic {}", topic);
                return;
            }
            if (key == null || "".equals(key.trim()) || " ".equals(key)) {
                log.warn("[processMessage] invalid key {}", key);
                return;
            }
            if (!new JsonValidatorUtil().validate(message)) {
                log.warn("[processMessage] invalid message string {}", message);
                return;
            }

            TypeReference<Map<String, Object>> type = new TypeReference<Map<String, Object>>() {
            };
            Map<String, Object> body = (Map<String, Object>) JsonUtil.json2Map(message, type);
            if (body == null || body.isEmpty()) {
                log.warn("[processMessage] invalid message string {}", message);
                return;
            }
            log.info("[processMessage][params]{}", message);
        } catch (Exception e) {
            log.info("[processMessage] exception", e);
        }

    }

}
