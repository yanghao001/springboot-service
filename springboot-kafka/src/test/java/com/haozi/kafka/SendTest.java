package com.haozi.kafka;

import com.haozi.kafka.message.KafkaProducer;
import com.haozi.kafka.utils.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hao.yang
 * @date 2019/7/9
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SendTest {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Test
    public void send() {
        String topic = "D2S-REQ";
        Map<String, Object> map = new HashMap<>();
        map.put("num1", 1);
        map.put("num2", 2);
        kafkaProducer.send(topic, "1111", JsonUtil.map2Json(map));
    }

}
