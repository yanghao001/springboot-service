package com.haozi.es6demo.Es;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * 删除数据
 * @author hao.yang
 * @date 2019/11/7
 */
@Slf4j
@SpringBootTest
public class DeleteData {

    @Test
    public void delete() {

        // 1. 开启
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

        // 2 索引Id删除
        DeleteResponse response1 = client.prepareDelete("twitter", "_doc", "1").get();

        // 2.0 条件批量删除
        BulkByScrollResponse response2 =
                DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
                        .filter(QueryBuilders.matchQuery("gender", "male"))
                        .source("persons")
                        .get();
        long deleted = response2.getDeleted();

        // 2.1 条件批量删除回调
        DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
                .filter(QueryBuilders.matchQuery("gender", "male"))
                .source("persons")
                .execute(new ActionListener<BulkByScrollResponse>() {
                    @Override
                    public void onResponse(BulkByScrollResponse response) {
                        long deleted = response.getDeleted();
                    }
                    @Override
                    public void onFailure(Exception e) {
                        // Handle the exception
                    }
                });
    }


}
