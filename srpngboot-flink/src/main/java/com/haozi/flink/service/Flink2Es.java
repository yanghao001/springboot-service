package com.haozi.flink.service;

import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.apache.flink.streaming.connectors.elasticsearch6.ElasticsearchSink;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ES6
 * @author hao.yang
 * @date 2019/9/24
 */
public class Flink2Es {

    /**
     * flink数据写入Es
     *
     * */
    public static void flinkToES() {
        Map<String, String> config = new HashMap<>();
        config.put("cluster.name", "my-cluster-name");
        config.put("bulk.flush.max.actions", "1");

        List<HttpHost> httpHosts = new ArrayList<>();
        httpHosts.add(new HttpHost("127.0.0.1", 9300, "http"));
        httpHosts.add(new HttpHost("10.2.3.1", 9300, "http"));
        DataStream<String> input = null;

        // ES6 特有构建方式
        ElasticsearchSink.Builder<String> esSinkBuilder = new ElasticsearchSink.Builder<>(httpHosts,
                new ElasticsearchSinkFunction<String>() {
                    public IndexRequest createIndexRequest(String element) {
                        Map<String, String> json = new HashMap<>();
                        json.put("data", element); // 将需要的数据添加到ES中

                        // 写入ES
                        return Requests.indexRequest()
                                .index("my-index")
                                .index("my-type")
                                .source(json);
                    }

                    @Override
                    public void process(String s, RuntimeContext runtimeContext, RequestIndexer requestIndexer) {
                        requestIndexer.add(createIndexRequest(s));
                    }
                });

        // 内置自定义配置
        esSinkBuilder.setBulkFlushMaxActions(1);
        esSinkBuilder.setRestClientFactory(
                restClientBuilder -> {
//                    restClientBuilder.setDefaultHeaders(...);
//                    restClientBuilder.setMaxRetryTimeoutMillis(...)
//                    restClientBuilder.setPathPrefix(...)
//                    restClientBuilder.setHttpClientConfigCallback(...)
                }
        );

        // 构建，将作业添加到通道中pipeline
        input.addSink(esSinkBuilder.build());
    }


    public static void main(String[] args) {
        flinkToES(); // 执行flink数据写入Es
    }

}
