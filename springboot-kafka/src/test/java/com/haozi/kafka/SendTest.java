package com.haozi.kafka;

import com.haozi.kafka.message.KafkaProducer;
import com.haozi.kafka.utils.GsonUtil;
import com.haozi.kafka.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hao.yang
 * @date 2019/7/9
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SendTest {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Test
    public void send() {
        String topic = "push-notification";
        Map<String, Object> map = new HashMap<>();
        map.put("num1", 1);
        map.put("num2", 2);
        kafkaProducer.send(topic, "1111", JsonUtil.map2Json(map));
    }

    @Test
    public void sendSetChargingProfileRequest() {
        Map<String, Object> params = new HashMap<>();
        params.put("OperationType", "SetChargingProfile");
        params.put("ConnectorId", "A");
        params.put("ChargingProfile", null);
        kafkaProducer.send("C2S-OCPP16-REQ-TOPIC", "CP3211", GsonUtil.jsonString(params));
    }

    @Test
    public void sendSetChargingProfileResponse() {
        Map<String, Object> params = new HashMap<>();
        params.put("OperationType", "SetChargingProfile");
        params.put("MessageId", 111);
        params.put("Status", "Accepted");
        kafkaProducer.send("D2S-OCPP16-REQ-TOPIC", "CP3211", GsonUtil.jsonString(params));
    }

    @Test
    public void sendData() {
        Map<String, Object> map = new HashMap<>();
        map.put("OperationType", "notification_charge_progress_detail");
        map.put("StartChargeSeq", "795671259190816022147733377");
        map.put("TransactionId", 1032612072);
        map.put("CurrentA", 1634);
        map.put("CurrentB", 1634);
        map.put("CurrentC", 1634);
        map.put("VoltageA", 22012);
        map.put("VoltageB", 22012);
        map.put("VoltageC", 22012);
        map.put("Soc", 0);
        map.put("Temperature", 354);
        map.put("StationId", 10002);
        map.put("Electricity", 1000);

        while (true) {

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                log.info("exception", e);
            }

            for (int i = 1; i < 9; i++) {
                String connectorId = String.valueOf(1);
                long now = System.currentTimeMillis() / 1000;
                map.put("Power", 3500);
                map.put("Timestamp", (int) now);
                map.put("UpdateTime", (int) now);
                map.put("ConnectorId", connectorId);
                kafkaProducer.send("push-notification", "abb" + 10000 + i, JsonUtil.map2Json(map));
                log.info("[test123456]send message[{}] success!", JsonUtil.map2Json(map));
            }

            for (int i = 1; i < 9; i++) {
                String connectorId = String.valueOf(1);
                long now = System.currentTimeMillis() / 1000;
                map.put("Power", 4000);
                map.put("Timestamp", (int) now);
                map.put("UpdateTime", (int) now);
                map.put("ConnectorId", connectorId);
                kafkaProducer.send("push-notification", "abb" + 10001 + i, JsonUtil.map2Json(map));
                log.info("[test123456]send message[{}] success!", JsonUtil.map2Json(map));
            }

            for (int i = 1; i < 9; i++) {
                String connectorId = String.valueOf(1);
                long now = System.currentTimeMillis() / 1000;
                map.put("Power", 4200);
                map.put("Timestamp", (int) now);
                map.put("UpdateTime", (int) now);
                map.put("ConnectorId", connectorId);
                kafkaProducer.send("push-notification", "abb" + 10002 + i, JsonUtil.map2Json(map));
                log.info("[test123456]send message[{}] success!", JsonUtil.map2Json(map));
            }

            for (int i = 1; i < 9; i++) {
                String connectorId = String.valueOf(1);
                long now = System.currentTimeMillis() / 1000;
                map.put("Power", 4500);
                map.put("Timestamp", (int) now);
                map.put("UpdateTime", (int) now);
                map.put("ConnectorId", connectorId);
                kafkaProducer.send("push-notification", "abb" + 10003 + i, JsonUtil.map2Json(map));
                log.info("[test123456]send message[{}] success!", JsonUtil.map2Json(map));
            }
        }

    }

    @Test
    public void testfor() {
        List<String> lists = new ArrayList<>();
        lists.add("haozi");
        for (String list : lists) {
            System.out.println("name:" + list);
        }
    }

    @Test
    public void test() {
        String connectorId = "test1234".split("-")[0];
        System.out.println("connectorId:" + connectorId);
    }

    @Test
    public void testFor() {
        long startTime = System.currentTimeMillis();
        List<String> list = new ArrayList<>(10000);
        int size = 10000;
        for (String str : list) {
            System.out.println("循环");
        }
        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + (endTime - startTime));
    }

    @Test
    public void localDateTimeTest() {
        long now = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).getEpochSecond();
        System.out.println("now=" + now);
    }

    @Test
    public void sendAzureData() {
        Map<String, Object> map = new HashMap<>();
        map.put("OperationType", "notification_charge_progress_detail");
        map.put("StartChargeSeq", "795671259190816022147733377");
        map.put("TransactionId", 1032612072);
        map.put("CurrentA", 1634);
        map.put("CurrentB", 1634);
        map.put("CurrentC", 1634);
        map.put("VoltageA", 22012);
        map.put("VoltageB", 22012);
        map.put("VoltageC", 22012);
        map.put("Soc", 0);
        map.put("Temperature", 354);
        map.put("StationId", 10002);
        map.put("Electricity", 1000);

        try {
            Thread.sleep(600000);
        } catch (InterruptedException e) {
            log.info("exception", e);
        }

        String connectorId = String.valueOf(1);
        long now = System.currentTimeMillis() / 1000;
        map.put("Power", 3500);
        map.put("Timestamp", (int) now);
        map.put("UpdateTime", (int) now);
        map.put("ConnectorId", connectorId);
        kafkaProducer.send("push-notification", "ABB00000001", JsonUtil.map2Json(map));
        log.info("[test123456]send message[{}] success!", JsonUtil.map2Json(map));
    }

}
