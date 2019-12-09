package com.haozi.es6demo.ESTest;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Es更新数据测试
 *
 * @author hao.yang
 * @date 2019/10/28
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UpdateData {

    @Autowired
    private TransportClient transportClient;

    /**
     * 更新数据 以json格式
     */
    @Test
    public void updateJson() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("article");
        updateRequest.type("_doc");
        updateRequest.id("1");
        updateRequest.doc(jsonBuilder()
                .startObject()
                .field("message", "这是更新过的文章！")
                .endObject());
        UpdateResponse updateResponse = null;
        try {
            updateResponse = transportClient.update(updateRequest).get();
        } catch (InterruptedException e) {
            log.warn("InterruptedException", e);
        } catch (ExecutionException e) {
            log.warn("ExecutionException", e);
        }
        log.info("response:{}", updateResponse);
    }

}
