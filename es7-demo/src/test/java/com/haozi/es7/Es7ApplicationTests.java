package com.haozi.es7;

import com.alibaba.fastjson.JSON;
import com.haozi.es7.model.Entity;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * eS6.4.3
 */
@SpringBootTest
class Es7ApplicationTests {

    private static final String host = "127.0.0.1";
    private static final int port = 9200;
    private static final String scheme = "http";
    private static final String INDEX_NAME = "indexName";
    private static final String CREATE_INDEX = "createIndex";
    private static RestHighLevelClient client;

    @Test
    void contextLoads() {
    }


    @BeforeEach
    public void init() {
        try {
            if (client != null) {
                client.close();
            }
            client = new RestHighLevelClient(RestClient.builder(new HttpHost(host, port, scheme)));
            // 1.创建索引
            CreateIndexRequest request = new CreateIndexRequest(INDEX_NAME);

            // 2.创建的每个索引都可以有与之关联的特定设置。
            request.settings(Settings.builder()
                    .put("index.number_of_shards", 1)
                    .put("index.number_of_replicas", 1));

            // 3.创建索引时创建文档类型映射
            request.mapping(CREATE_INDEX, XContentType.JSON);
//            request.mapping(
//                    "  {\n" +
//                            "    \"tweet\": {\n" +
//                            "      \"properties\": {\n" +
//                            "        \"message\": {\n" +
//                            "          \"type\": \"text\"\n" +
//                            "        }\n" +
//                            "      }\n" +
//                            "    }\n" +
//                            "  }", //类型映射，需要的是一个JSON字符串
//                    XContentType.JSON);

            // 4.为索引设置一个别名
            request.alias(
                    new Alias("twitter_alias")
            );

            // 可选参数
            request.setTimeout(TimeValue.timeValueMinutes(2));//超时,等待所有节点被确认(使用TimeValue方式)
            //request.timeout("2m");//超时,等待所有节点被确认(使用字符串方式)
            request.setMasterTimeout(TimeValue.timeValueMinutes(1));//连接master节点的超时时间(使用TimeValue方式)
            request.masterNodeTimeout();//连接master节点的超时时间(使用字符串方式)

            // 5.同步执行
            CreateIndexResponse res = client.indices().create(request, RequestOptions.DEFAULT);
            if (!res.isAcknowledged()) {
                throw new RuntimeException("initialize failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @AfterEach
    public void close() throws IOException {
        if (client != null) {
            client.close();
        }
        client = null;
    }

    @Test
    public void ESTest() {
        Entity entity = new Entity();
        entity.setId(INDEX_NAME);
        entity.setData("这是一条数据！！！！");
    }


    public void insertOrUpdateOne(String index, Entity entity) {
        IndexRequest request = new IndexRequest(index);
        request.id(entity.getId());
        request.source(entity.getData(), XContentType.JSON);
        try {
            client.index(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量插入
     */
    public void insertBatch(String index, List<Entity> list) {
        BulkRequest request = new BulkRequest();
        list.forEach(item -> request.add(new IndexRequest(index).id(item.getId())
                .source(item.getData(), XContentType.JSON)));
        try {
            client.bulk(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量删除
     */
//    public <T> void deleteBatch(String index, Collection<T> idList) {
//        BulkRequest request = new BulkRequest();
//        idList.forEach(item -> request.add(new DeleteRequest(index, item.toString())));
//        try {
//            client.bulk(request, RequestOptions.DEFAULT);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    /**
     * 搜索
     * 通过构建SearchSourceBuilder查询参数
     */
    public <T> List<T> search(String index, SearchSourceBuilder builder, Class<T> c) {
        SearchRequest request = new SearchRequest(index);
        request.source(builder);
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            SearchHit[] hits = response.getHits().getHits();
            List<T> res = new ArrayList<>(hits.length);
            for (SearchHit hit : hits) {
                res.add(JSON.parseObject(hit.getSourceAsString(), c));
            }
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * es 插入数据容易，删除就比较麻烦了，特别是根据条件删除。
     */
    public void deleteByQuery(String index, QueryBuilder builder) {
        DeleteByQueryRequest request = new DeleteByQueryRequest(index);
        request.setQuery(builder);
        //设置批量操作数量,最大为10000
        request.setBatchSize(10000);
        request.setConflicts("proceed");
        try {
            client.deleteByQuery(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
