package com.haozi.es7.estest;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 *  Es 删除数据测试
 * @author hao.yang
 * @date 2019/10/28
 */
@Slf4j
@SpringBootTest
public class DeleteTest {

    /**
     * 删除数据（索引删除）
     */
    @Test
    private void delete() throws IOException {
        // 1. 初始化
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("192.168.1.240", 9200, "http")));
        DeleteRequest request = new DeleteRequest(
                "log",
                "1");
        DeleteResponse deleteResponse = client.delete(
                request, RequestOptions.DEFAULT);
        log.info("deleteResponse:{}", deleteResponse);
    }

}
