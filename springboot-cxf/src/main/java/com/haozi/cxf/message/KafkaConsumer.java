package com.haozi.cxf.message;

import com.haozi.cxf.config.MessageConstant;
import com.haozi.cxf.utils.JacksonUtil;
import com.haozi.cxf.utils.JsonValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yanghao
 * @Description: 消费centralService发送的响应(messageId/status)，转发到chargePoint
 * @date 2019/2/12 14:06
 */
@Slf4j
@Component
public class KafkaConsumer {

    public ConcurrentHashMap<Object, Object> hashMap = new ConcurrentHashMap();

    @KafkaListener(topics = {MessageConstant.OCPP16_S2D_TOPIC})
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
            if (!new JsonValidator().validate(message)) {
                log.warn("[processMessage] invalid message string {}", message);
                return;
            }

            Map<String, Object> body = JacksonUtil.json2Bean(message, Map.class);
            if (body == null || body.isEmpty() || !body.containsKey("OperationType")) {
                log.warn("[processMessage] invalid message string {}", message);
                return;
            }
            String operationType = (String) body.get("OperationType");

            String messageId = (String) body.get("MessageId");
            if (StringUtils.isBlank(messageId)) {
                log.info("messageId invalid");
                return;
            }

            // 根据不同的operatorType，应答chargePoint
            switch (operationType) {
                case MessageConstant.D2S_AUTHORIZE_REQ:
                    String authorizeStatus = (String) body.get("Status");
                    hashMap.put(messageId, authorizeStatus);
                    hashMap.put("OperatorType" + messageId, MessageConstant.D2S_AUTHORIZE_REQ);
                    break;

                case MessageConstant.D2S_BOOT_NOTIFICATION_REQ:
                    String bootStatus = (String) body.get("Status");
                    hashMap.put(messageId, bootStatus);
                    hashMap.put("OperatorType" + messageId, MessageConstant.D2S_BOOT_NOTIFICATION_REQ);
                    break;
                case MessageConstant.D2S_START_TRANSACTION_REQ:
                    String startStatus = (String) body.get("Status");
                    if (Objects.isNull(body.get("TransactionId"))) {
                        log.info("TransactionId invalid");
                        return;
                    }
                    Integer transactionId = (Integer) body.get("TransactionId");
                    hashMap.put(messageId, startStatus);
                    hashMap.put("TransactionId" + messageId, transactionId);
                    hashMap.put("OperatorType" + messageId, MessageConstant.D2S_START_TRANSACTION_REQ);
                    break;
                case MessageConstant.D2S_STOP_TRANSACTION_REQ:
                    String stopStatus = (String) body.get("Status");
                    hashMap.put(messageId, stopStatus);
                    hashMap.put("OperatorType" + messageId, MessageConstant.D2S_STOP_TRANSACTION_REQ);
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            log.info("KafkaConsumer Exception:{}", e);
        }
    }
}


