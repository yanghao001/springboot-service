package com.haozi.es6demo.ESTest;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequestBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * Es查询数据测试
 *
 * @author hao.yang
 * @date 2019/10/28
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class GetData {

    @Autowired
    private TransportClient transportClient;

    /**
     * 查询数据 Get Api
     */
    @Test
    public void getById() {
        // 条件查询
        Map<String, Object> template_params = new HashMap<>();
        template_params.put("message_param", "更新");
        GetResponse response = transportClient.prepareGet("article", "_doc", "1").get();
        log.info("SearchResponse:{}", response);
    }

    /**
     * 结构化查询数据 Get Api
     */
    @Test
    public void getByMatch() {
        // 条件查询
        Map<String, Object> template_params = new HashMap<>();
        template_params.put("message_param", "更新");
        SearchResponse response = new SearchTemplateRequestBuilder(transportClient)
                .setScript("{\n" +
                        "        \"query\" : {\n" +
                        "            \"match\" : {\n" +
                        "                \"message\" : \"{{message_param}}\"\n" +
                        "            }\n" +
                        "        }\n" +
                        "}")
                .setScriptType(ScriptType.INLINE)
                .setScriptParams(template_params)
                .setRequest(new SearchRequest())
                .get()
                .getResponse();
        log.info("SearchResponse:{}", response);
    }


}
