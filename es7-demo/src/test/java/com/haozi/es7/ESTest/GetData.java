package com.haozi.es7.ESTest;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 *  Es查询数据测试
 * @author hao.yang
 * @date 2019/10/28
 */
@Slf4j
@SpringBootTest
public class GetData {

    /**
     * 查询数据 Get Api
     */
    @Test
    public void get() throws IOException {

        // 初始化
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));
        GetRequest request = new GetRequest("data", "2");
        try {
            GetResponse getResponse = client.get(request, RequestOptions.DEFAULT);
            log.info("getResponse:{}", getResponse);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {
                log.info("getResponse:{}", RestStatus.NOT_FOUND);
            }
        }
    }


}
