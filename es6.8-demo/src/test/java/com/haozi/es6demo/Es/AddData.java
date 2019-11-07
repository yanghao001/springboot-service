package com.haozi.es6demo.Es;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author hao.yang
 * @date 2019/11/7
 */
@Slf4j
@SpringBootTest
public class AddData {

    @Test
    public void add() throws IOException {
        // 1. on startup
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

        // 2. doc
        XContentBuilder builder = jsonBuilder()
                .startObject()
                .field("user", "haozi")
                .field("Date", new Date())
                .field("text", "这是一篇文章！")
                .endObject();
        String json = Strings.toString(builder);
        IndexResponse response = client.prepareIndex("Article", "_doc")
                .setSource(json, XContentType.JSON)
                .get();

        // 3. response
        String _index = response.getIndex(); // Index name
        String _type = response.getType(); // Type name
        String _id = response.getId(); // Document ID (generated or not)
        long _version = response.getVersion();
        RestStatus status = response.status(); // status has stored current instance statement.

        // 4. shutdown
        client.close();
    }

}
