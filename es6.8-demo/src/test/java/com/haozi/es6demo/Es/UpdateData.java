package com.haozi.es6demo.Es;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author hao.yang
 * @date 2019/11/7
 */
@Slf4j
@SpringBootTest
public class UpdateData {

    @Test
    public void update() throws IOException, ExecutionException, InterruptedException {

        // 开启
        Settings settings = Settings.builder()
                .put("cluster.name", "myClusterName").build();
        TransportClient client = null;
        try {
            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300))
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("host2"), 9300));
        } catch (UnknownHostException e) {
            log.info("UnknownHostException", e);
        }

        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("index");
        updateRequest.type("_doc");
        updateRequest.id("1");
        updateRequest.doc(jsonBuilder()
                .startObject()
                .field("gender", "male")
                .endObject());
        client.update(updateRequest).get();
    }

}
