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
        String dateString = formatter.format(new Date());
        Map<String, Object> map = new HashMap<>();
        map.put("OperationType", ConstantConfig.D_REPORT_CHARGE_PROGRESS);
        map.put("OrderNumber", "123456");
        map.put("StartTime", dateString);
        map.put("Voltage", "5");
        map.put("ElecCurrent", "100.0");
        map.put("Soc", "100");
        map.put("Temperature", "40");
        kafkaTemplate.send(ConstantConfig.D2S_TOPIC, "test112233", JsonUtil.map2Json(map));
        log.info("send message success!");
    }

}
