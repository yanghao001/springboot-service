package com.haozi.mongodb;

import com.haozi.mongodb.config.ConstantConfig;
import com.haozi.mongodb.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongodbApplicationTests {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Test
    public void contextLoads() {
    }

    @Test
    public void insertChargeInfo() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long now = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
        map.put("OperationType", ConstantConfig.D_REPORT_CHARGE_PROGRESS);
        map.put("StartChargeSeq", "654321");
        map.put("StartTime", (int) now);
        map.put("Soc", "0");
        map.put("TotalPower", "40");
        map.put("ConnectorId", "test123456-A");
        kafkaTemplate.send(ConstantConfig.D2S_TOPIC, "test123456", JsonUtil.map2Json(map));
        log.info("send message success!");
    }

}
