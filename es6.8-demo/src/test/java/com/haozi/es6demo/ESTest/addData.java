package com.haozi.es6demo.ESTest;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Date;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Es添加数据测试
 *
 * @author hao.yang
 * @date 2019/10/28
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class addData {

    @Autowired
    private TransportClient transportClient;

    /**
     * TranportClient
     * 添加索引数据
     */
    @Test
    public void index() {
        try {
            XContentBuilder builder = jsonBuilder()
                    .startObject()
                    .field("user", "haozi")
                    .field("postDate", new Date())
                    .field("message", "这是一篇文章！")
                    .endObject();
            String json = Strings.toString(builder);
            IndexResponse response = transportClient.prepareIndex("article", "_doc").setId("2")
                    .setSource(json, XContentType.JSON)
                    .get();

            // response
            log.info("index response:{}", response);

            // shutdown
            transportClient.close();
        } catch (IOException e) {
            log.warn("IOException", e);
        }
    }

}
