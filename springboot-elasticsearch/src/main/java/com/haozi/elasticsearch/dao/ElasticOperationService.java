package com.haozi.elasticsearch.dao;

import com.haozi.elasticsearch.utils.JsonUtil;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * @author hao.yang
 * @date 2019/9/19
 */
@Component
public class ElasticOperationService {

    private final Logger logger = LoggerFactory.getLogger(ElasticOperationService.class);

    @Autowired
    private Client client;

    private BulkProcessor bulkProcessor;

    @PostConstruct
    public void initBulkProcessor() {
        bulkProcessor = BulkProcessor.builder(client, new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                logger.info("序号：{} 开始执行{} 条记录保存", executionId, request.numberOfActions());
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                logger.error(String.format("序号：%s 执行失败; 总记录数：%s", executionId, request.numberOfActions()), failure);
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                logger.info("序号：{} 执行{}条记录保存成功,耗时：{}毫秒,", executionId, request.numberOfActions(), response.getTookInMillis());
            }
        }).setBulkActions(1000)
                .setBulkSize(new ByteSizeValue(10, ByteSizeUnit.MB))
                .setConcurrentRequests(4)
                .setFlushInterval(TimeValue.timeValueSeconds(5))
                .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(500), 3))  //失败后等待多久及重试次数
                .build();
    }

    @PreDestroy
    public void closeBulk() {
        if (bulkProcessor != null) {
            try {
                bulkProcessor.close();
            } catch (Exception e) {
                logger.error("close bulkProcessor exception", e);
            }
        }
    }

    /**
     * 创建索引
     */
    public void createIndex() {
        client.admin().indices().prepareCreate("test1").get();
        client.close();
    }

    /**
     * 删除索引
     */
    public void deleteIndex() {
        client.admin().indices().prepareDelete("test1").get();
        client.close();
    }

    /**
     * 批量添加,性能最好
     */
    public void addDocumentToBulkProcessor(String indices, String type, Object object) {
        bulkProcessor.add(client.prepareIndex(indices, type).setSource(JsonUtil.bean2Json(object)).request());
    }

    /**
     * 添加数据1
     */
    @SuppressWarnings("unchecked")
    public void addData() {
        Map data = new HashMap();
        data.put("indices", "test");
        data.put("type", System.currentTimeMillis());
        IndexResponse response = client.prepareIndex("test", "log", UUID.randomUUID().toString()).setSource(data).get();
        logger.info("添加结果：{}", response.toString());
        client.close();
    }

    /**
     * 添加数据2
     */
    public void addDocument(String indices, String type, Object object) {
        IndexResponse resp = client.prepareIndex(indices, type).setSource(JsonUtil.bean2Json(object)).get();
        logger.info("添加结果：{}", resp.toString());
        client.close();
    }

    /**
     * 按id删除
     */
    public void deleteDocumentById(String index, String type, String id) {
        DeleteResponse resp = client.prepareDelete(index, type, id).get();
        logger.info("删除结果：{}", resp.toString());
        client.close();
    }

    /**
     * 按ID更新
     */
    public void updateDocument(String indices, String type, String id, Object object) {
        UpdateResponse resp = client.prepareUpdate(indices, type, id).setDoc(JsonUtil.bean2Json(object)).get();
        logger.info("更新结果：{}", resp.toString());
        client.close();
    }

    /**
     * 创建文档以json格式
     */
    public void createIndexByJson() {
        String json = "{" + "\"id\":\"1\"," + "\"title\":\"基于Lucene的搜索服务器\","
                + "\"content\":\"它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口\"" + "}";
        //创建
        IndexResponse res = client.prepareIndex("test1", "test", "1").setSource(json).execute().actionGet();
        logger.info("索引={}， 类型={}， id={}， 版本号={}， 结果={}",
                res.getIndex(), res.getType(), res.getId(), res.getVersion(), res.getResult());
        client.close();
    }

    /**
     * 创建文档以hashMap
     */
    public void createIndexByMap() {
        HashMap<String, Object> json = new HashMap<String, Object>();
        json.put("id", "1");
        json.put("title", "测试1");
        json.put("content", "测试内容");
        IndexResponse res = client.prepareIndex("blog", "article", "2").setSource(json).execute().actionGet();
        logger.info("索引={}， 类型={}， id={}， 版本号={}， 结果={}",
                res.getIndex(), res.getType(), res.getId(), res.getVersion(), res.getResult());
        client.close();
    }

    /**
     * 创建文档以builder
     *
     */
    public void createIndexByBuilder() throws IOException {
        XContentBuilder b = XContentFactory.jsonBuilder()
                .startObject()
                .field("id", "2")
                .field("title", "测试2")
                .field("content", "测试内容")
                .endObject();
        IndexResponse res = client.prepareIndex("blog", "article", "3").setSource(b).execute().actionGet();
        logger.info("索引={}， 类型={}， id={}， 版本号={}， 结果={}",
                res.getIndex(), res.getType(), res.getId(), res.getVersion(), res.getResult());
        client.close();
    }

    /**
     * 单个索引查询
     * */
    public void queryIndex() {
        GetResponse res = client.prepareGet("blog", "article", "3").get();
        logger.info("查询结果:{}", res.getSourceAsString());
        client.close();
    }

    /**
     * 多个查询
     **/
    public void multiQueryIndex() {
        MultiGetResponse res = client.prepareMultiGet().add("blog", "article", "3")
                .add("blog", "article", "2","3")
                .add("blog", "article", "1").get();
        for (MultiGetItemResponse multiGetItemResponse:res) {
            GetResponse response = multiGetItemResponse.getResponse();
            if (response.isExists()) {
                logger.info("查询结果：{}", response.getSourceAsString());
            }
        }
    }

    /**
     * 更新文档数据
     * */
    public void update() throws IOException, ExecutionException, InterruptedException {
        UpdateRequest up = new UpdateRequest("blog", "article", "2");
        up.doc(XContentFactory.jsonBuilder().startObject()
                .field("id","2")
                .field("title","测试2")
                .field("content","更新测试内容")
                .endObject());
        UpdateResponse res = client.update(up).get();
        logger.info("索引={}， 类型={}， id={}， 版本号={}， 结果={}",
                res.getIndex(), res.getType(), res.getId(), res.getVersion(), res.getResult());
        client.close();
    }

    /**
     * 没有创建，就更新数据
     * */
    public void upset() throws IOException, ExecutionException, InterruptedException {
        //没有这个文档内容就创建
        IndexRequest index = new IndexRequest("blog", "article", "5");
        IndexRequest re1 = index.source(XContentFactory.jsonBuilder().startObject()
                .field("id", 5)
                .field("title", "测试5")
                .field("content", "测试内容")
                .endObject());
        //有文档内容就更新
        UpdateRequest updateRequest = new UpdateRequest("blog", "article", "5");
        updateRequest.doc(XContentFactory
                .jsonBuilder()
                .startObject()
                .field("id","5")
                .field("title","test")
                .field("content","111")
                .endObject());
        updateRequest.upsert(re1);
        //具体更新操作
        UpdateResponse res = client.update(updateRequest).get();
        logger.info("索引={}， 类型={}， id={}， 版本号={}， 结果={}",
                res.getIndex(), res.getType(), res.getId(), res.getVersion(), res.getResult());
        client.close();
    }

    /**
     * 删除文档
     * */
    public void deleteDoc() {
        client.prepareDelete("blog", "article", "2");
        client.close();
    }

    /**
     * 查询全部
     * */
    public void queryAllMacth() {
        SearchResponse response = client.prepareSearch("blog").setTypes("article")
                .setQuery(QueryBuilders.matchAllQuery()).get();
        // 获取查询对象
        SearchHits hits = response.getHits();
        logger.info("查询结果：{}", hits.getTotalHits());

        // 打印文档内容
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()) {
            SearchHit next = iterator.next();
            logger.info("文档内容：{}", next.getSourceAsString());
        }
        client.close();
    }

    /**
     * 分词查询
     *
     * */
    public void query1() {
        SearchResponse response = client.prepareSearch("blog")
                .setTypes("article")
                .setQuery(QueryBuilders.queryStringQuery("测试"))
                .get();
        // 获取查询对象
        SearchHits hits = response.getHits();
        logger.info("查询结果：{}", hits.getTotalHits());

        // 打印文档内容
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()) {
            SearchHit next = iterator.next();
            logger.info("文档内容：{}", next.getSourceAsString());
        }
        client.close();
    }


    /**
     * 词条查询
     *
     * */
    public void query2() {
        SearchResponse response = client.prepareSearch("blog")
                .setTypes("article")
                .setQuery(QueryBuilders.termQuery("content", "测试"))
                .get();
        // 获取查询对象
        SearchHits hits = response.getHits();
        logger.info("查询结果：{}", hits.getTotalHits());

        // 打印文档内容
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()) {
            SearchHit next = iterator.next();
            logger.info("文档内容：{}", next.getSourceAsString());
        }
        client.close();
    }

    /**
     * 通配符查询  * 表示多个字符（任意字符） ？ 表示单个字符
     * */
    public void query3() {
        SearchResponse response = client.prepareSearch("blog")
                .setTypes("article")
                .setQuery(QueryBuilders.wildcardQuery("content", "*测试*"))
                .get();
        // 获取查询对象
        SearchHits hits = response.getHits();
        logger.info("查询结果：{}", hits.getTotalHits());

        // 打印文档内容
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()) {
            SearchHit next = iterator.next();
            logger.info("文档内容：{}", next.getSourceAsString());
        }
        client.close();
    }

    /**
     * 模糊查询
     * */
    public void query4() {
        SearchResponse response = client.prepareSearch("blog")
                .setTypes("article")
                .setQuery(QueryBuilders.fuzzyQuery("content", "测试"))
                .get();
        // 获取查询对象
        SearchHits hits = response.getHits();
        logger.info("查询结果：{}", hits.getTotalHits());

        // 打印文档内容
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()) {
            SearchHit next = iterator.next();
            logger.info("文档内容：{}", next.getSourceAsString());
        }
        client.close();
    }

    /**
     * 映射相关操作  新创建一个index  没有mapping映射才能运行
     *
     */
    public void createMapping() throws Exception {
        // 1设置mapping
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .startObject("article")
                .startObject("properties")
                .startObject("id1")
                .field("type", "string")
                .field("store", "yes")
                .endObject()
                .startObject("title2")
                .field("type", "string")
                .field("store", "no")
                .endObject()
                .startObject("content")
                .field("type", "string")
                .field("store", "yes")
                .endObject()
                .endObject()
                .endObject()
                .endObject();

        // 2 添加mapping
        PutMappingRequest mapping = Requests.putMappingRequest("blog4").type("article").source(builder);
        client.admin().indices().putMapping(mapping).get();
        // 3 关闭资源
        client.close();
    }
}
