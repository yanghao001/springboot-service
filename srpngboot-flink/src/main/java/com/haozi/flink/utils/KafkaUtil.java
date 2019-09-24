package com.haozi.flink.utils;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author hao.yang
 * @date 2019/9/23
 */
public class KafkaUtil {

    public static final String broker_list = "localhos:9090";
    public static final String topic = "student";

    public static void writeToKafka() throws InterruptedException {
        Properties props= new Properties();
        props.put("bootstrap.servers", broker_list);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer producer = new KafkaProducer<String, String>(props);

        for (int i=0; i<=100; i++) {
            Map<Integer, Object> map = new HashMap<>();
            map.put(i, "haozi" + i);
            ProducerRecord record = new ProducerRecord<String, String>(topic, null, null, JsonUtil.map2Json(map));
            producer.send(record);
            System.out.println("发送数据:" + JsonUtil.map2Json(map));
            Thread.sleep(10 * 1000);
        }
        producer.flush();
    }

    public static void main(String[] args) throws InterruptedException{
        writeToKafka();
    }

}
