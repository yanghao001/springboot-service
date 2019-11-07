package com.haozi.es6demo.Es;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequestBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hao.yang
 * @date 2019/11/7
 */
@Slf4j
@SpringBootTest
public class QueryData {

    @Test
    public void query() {
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

        // 2. 查询
        GetResponse response1 = client.prepareGet("twitter", "_doc", "1").get();

        // 3. 条件查询
        SearchRequestBuilder srb1 = client
                .prepareSearch().setQuery(QueryBuilders.queryStringQuery("elasticsearch")).setSize(1);
        SearchRequestBuilder srb2 = client
                .prepareSearch().setQuery(QueryBuilders.matchQuery("users", "haozi")).setSize(1);
        MultiSearchResponse sr = client.prepareMultiSearch()
                .add(srb1)
                .add(srb2)
                .get();

        // You will get all individual responses from MultiSearchResponse#getResponses()
        long nbHits = 0;
        for (MultiSearchResponse.Item item : sr.getResponses()) {
            SearchResponse response = item.getResponse();
            nbHits += response.getHits().getTotalHits();
        }
    }

    /**
     *
     * */
    public void searchStore() {
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

//        client.admin().cluster().preparePutStoredScript()
//                .setScriptLang("mustache")
//                .setId("template_gender")
//                .setSource(new BytesArray(
//                        "{\n" +
//                                "    \"query\" : {\n" +
//                                "        \"match\" : {\n" +
//                                "            \"gender\" : \"{{param_gender}}\"\n" +
//                                "        }\n" +
//                                "    }\n" +
//                                "}")).get();
//
//        // 查询方式1
//        Map<String, Object> template_params = new HashMap<>();
//        template_params.put("param_gender", "male");
//        SearchResponse sr = new SearchTemplateRequestBuilder(client)
//                .setScript("template_gender")
//                .setScriptType(ScriptType.STORED)
//                .setScriptParams(template_params)
//                .setRequest(new SearchRequest())
//                .get()
//                .getResponse();
    }

    /**
     *  inline 方式查询
     * */
    public void searchDoc() {

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

        // 2. query
        Map<String, Object> template_params = new HashMap<>();
        template_params.put("param_gender", "male");
        SearchResponse sr = new SearchTemplateRequestBuilder(client)
                .setScript("{\n" +
                        "        \"query\" : {\n" +
                        "            \"match\" : {\n" +
                        "                \"gender\" : \"{{param_gender}}\"\n" +
                        "            }\n" +
                        "        }\n" +
                        "}")
                .setScriptType(ScriptType.INLINE)
                .setScriptParams(template_params)
                .setRequest(new SearchRequest())
                .get()
                .getResponse();
    }


}
