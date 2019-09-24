package com.haozi.flink.utils;

import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch6.ElasticsearchSink;
import org.apache.http.HttpHost;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hao.yang
 * @date 2019/9/23
 */
public class ElasticSearchSinkYtil {

    /**
     * 解析配置文件
     *
     * */
    public static List<HttpHost> getEsAddress(String hosts) throws MalformedURLException {
        String[] hostList = hosts.split(",");
        List<HttpHost> addresses = new ArrayList<>();
        for (String host: hostList) {
            if (host.startsWith("http")) {
                URL url = new URL(host);
                addresses.add(new HttpHost(url.getHost(), url.getPort()));
            } else {
                String[] parts = host.split(":", 2);
                if (parts.length > 1) {
                    addresses.add(new HttpHost(parts[0], Integer.parseInt(parts[1])));
                } else {
                    throw new MalformedURLException("invalid elastissearch hosts format");
                }
            }
        }
        return addresses;
    }


    public static <T> void addSink(List<HttpHost> hosts, int bulkFlushMaxActions, int parallelism,
                                   SingleOutputStreamOperator<T> data, ElasticsearchSinkFunction<T> func) {
        ElasticsearchSink.Builder<T> esSinkBuilder = new ElasticsearchSink.Builder<>(hosts, func);
        esSinkBuilder.setBulkFlushMaxActions(bulkFlushMaxActions);
        data.addSink(esSinkBuilder.build()).setParallelism(parallelism);
    }



}
