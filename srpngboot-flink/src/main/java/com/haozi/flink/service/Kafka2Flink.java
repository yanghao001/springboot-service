package com.haozi.flink.service;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

/**
 * flink消费kafka消息
 * @author hao.yang
 * @date 2019/9/24
 */
public class Kafka2Flink {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9090");
        properties.setProperty("group.id", "test");
        //构建FlinkKafkaConsumer
        FlinkKafkaConsumer<String> myConsumer = new FlinkKafkaConsumer<>("topic", new SimpleStringSchema(), properties);
        //指定偏移量
        myConsumer.setStartFromEarliest();
        DataStream<String> stream = env.addSource(myConsumer);
        env.enableCheckpointing(5000);
        stream.print();
        env.execute("Flink Streaming Java API Skeleton");
    }
}
