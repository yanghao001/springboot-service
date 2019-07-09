package com.haozi.cxf.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author yanghao
 * @Description: 生产者
 * @date 2019/2/12 14:06
 */
@Slf4j
@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public ListenableFuture<SendResult<Integer, String>> send(String topic, String key, String message) {

        ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.send(topic, key, message);
        future.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                log.info("send message topic-->{}, key-->{}, message-->{} success", topic, key, result.getProducerRecord().value());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.warn("send fail message-->{} ex-->{}", message, ex.getMessage());
            }

        });

        return future;
    }
}

