package com.haozi.es7.estest;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Es更新数据测试
 * @author hao.yang
 * @date 2019/10/28
 */
@Slf4j
@SpringBootTest
public class UpdateTest {

    /**
     * 更新数据 以json格式
     */
    @Test
    private void updateJson() throws IOException {
        // 初始化
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("192.168.1.240", 9200, "http")));
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("logType", "Device");
        jsonMap.put("transferType", "Bluetooth");
        jsonMap.put("generateTime", "2019-10-29");
        jsonMap.put("deviceId", "ASE0000001");
        jsonMap.put("port", "1");
        jsonMap.put("operationType", "startTransaction");
        jsonMap.put("messageType", "request");
        jsonMap.put("logInfo", "log info");
        UpdateRequest request = new UpdateRequest(
                "log",
                "1");
        request.doc(jsonMap);
        UpdateResponse updateResponse = client.update(request, RequestOptions.DEFAULT);
        log.info("response:{}", updateResponse);
    }

    /**
     * 更新数据 以doc格式
     */
    @Test
    private void updateDoc() throws IOException {
        // 初始化
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("192.168.1.240", 9200, "http")));
        UpdateRequest request = new UpdateRequest(
                "log",
                "1");
        String jsonString = "{" +
                "\"generateTime\":\"2017-01-01\"," +
                "\"logInfo\":\"daily update\"" +
                "}";
        request.doc(jsonString, XContentType.JSON);
        UpdateResponse updateResponse = client.update(request, RequestOptions.DEFAULT);
        log.info("response:{}", updateResponse);
    }

    /**
     * 更新数据 以map to json格式
     */
    @Test
    private void updateMapTOJson() throws IOException {
        // 初始化
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("192.168.1.240", 9200, "http")));
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.timeField("generateTime", new Date());
            builder.field("logInfo", "daily one");
        }
        builder.endObject();
        UpdateRequest request = new UpdateRequest("log", "1")
                .doc(builder);
        UpdateResponse updateResponse = client.update(request, RequestOptions.DEFAULT);
        log.info("response:{}", updateResponse);
    }

    /**
     * 更新插入数据
     * */
    @Test
    private void updateInsert() throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("192.168.1.240", 9200, "http")));
        String jsonString = "{" +
                "\"generateTime\":\"2020-01-01\"," +
                "\"logInfo\":\"daily update\"" +
                "}";
        UpdateRequest request = new UpdateRequest(
                "log",
                "1");
        request = request.upsert(jsonString, XContentType.JSON);
        UpdateResponse updateResponse = client.update(request, RequestOptions.DEFAULT);
        log.info("response:{}", updateResponse);
    }


}
