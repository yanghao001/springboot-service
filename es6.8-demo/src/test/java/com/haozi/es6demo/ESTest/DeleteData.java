package com.haozi.es6demo.ESTest;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *  Es 删除数据测试
 * @author hao.yang
 * @date 2019/10/28
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DeleteData {

    @Autowired
    private TransportClient transportClient;

    /**
     * 删除数据（索引删除）
     */
    @Test
    public void delete() {
        // 索引Id删除
        DeleteResponse deleteResponse = transportClient.prepareDelete("article", "_doc", "1").get();
        log.info("deleteResponse:{}", deleteResponse);
    }

}
