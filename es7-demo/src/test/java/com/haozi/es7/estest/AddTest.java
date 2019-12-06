package com.haozi.es7.estest;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *  Es添加数据测试
 * @author hao.yang
 * @date 2019/10/28
 */
@Slf4j
@SpringBootTest
public class AddTest {

    /**
     * TranportClient
     * 添加索引数据
     */
    @Test
    private void index() {
        try {
            // 1. 初始化
            RestHighLevelClient client = new RestHighLevelClient(
                    RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));

            // 2. 创建索引
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("logType", "Device");
            jsonMap.put("transferType", "Bluetooth");
            jsonMap.put("generateTime", new Date().toString());
            jsonMap.put("deviceNumber", "ASE00000001");
            jsonMap.put("port", "1");
            jsonMap.put("operationType", "Bootification");
            jsonMap.put("messageType", "request");
            jsonMap.put("logInfo", "log info");
            IndexRequest indexRequest = new IndexRequest("data").id("2").source(jsonMap);

            // 3. 条件设置
            indexRequest.routing("routing");
            indexRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
            indexRequest.setRefreshPolicy("wait_for");

            // 4. 监听
            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            log.info("indexResponse:{}", indexResponse.toString());
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
