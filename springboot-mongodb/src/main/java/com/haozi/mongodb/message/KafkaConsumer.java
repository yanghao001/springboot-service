package com.haozi.mongodb.message;

import com.haozi.mongodb.config.ConstantConfig;
import com.haozi.mongodb.model.ChargeInfo;
import com.haozi.mongodb.utils.JsonUtil;
import com.haozi.mongodb.utils.JsonValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * @author hao.yang
 * @date 2019/7/31
 */
@Slf4j
@Component
public class KafkaConsumer {

    @Autowired
    private MongoTemplate mongoTemplate;

    @KafkaListener(topics = ConstantConfig.D2S_TOPIC)
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
            log.info("[processMessage][params]{}", message);
            saveData(key, message);
        } catch (Exception e) {
            log.info("processMessage exception", e);

        }
    }

    /**
     * 存储充电过程中充电数据
     */
    public void saveData(String deviceNumber, String message) throws ParseException {
        Map data = JsonUtil.json2Bean(message, Map.class);
        String operationType = (String) data.get("OperationType");
        if (ConstantConfig.D_REPORT_CHARGE_PROGRESS.equals(operationType)) {
            int timestamp = (int) data.get("Timestamp");
            Integer connectorId = (Integer) data.get("ConnectorId");
            int transactionId = (int) data.get("TransactionId");
            String voltage = (String) data.get("Voltage");
            String current = (String) data.get("Current");
            ChargeInfo chargeInfo = new ChargeInfo();
            chargeInfo.setTimestamp(timestamp);
            chargeInfo.setVoltage(voltage);
            chargeInfo.setElecCurrent(current);
            chargeInfo.setTransactionId(transactionId);
            chargeInfo.setDeviceNumber(deviceNumber + "-" + connectorId);

            try {
                if (!mongoTemplate.collectionExists(ConstantConfig.MONGO_CHARGE_GRAPE_DATA)) {
                    mongoTemplate.createCollection(ConstantConfig.MONGO_CHARGE_GRAPE_DATA);
                }
                // 存入mongo数据库
                mongoTemplate.insert(chargeInfo, ConstantConfig.MONGO_CHARGE_GRAPE_DATA);
                log.info("insert mongo succeed!");
            } catch (Exception e) {
                log.info("exception", e);
            }
        }
    }


}
