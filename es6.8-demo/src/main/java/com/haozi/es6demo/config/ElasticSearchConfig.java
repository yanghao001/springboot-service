package com.haozi.es6demo.config;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author hao.yang
 * @date 2019/11/9
 */
@Slf4j
@Configuration
public class ElasticSearchConfig {

    @Value("${elasticsearch.ip}")
    private String hostName;

    /**
     * 端口
     */
    @Value("${elasticsearch.port}")
    private int port;

    /**
     * 集群名称
     */
    @Value("${elasticsearch.cluster-name}")
    private String clusterName;

    /**
     * 集群名称
     */
    @Value("${elasticsearch.node-name}")
    private String nodeName;

    /**
     * Bean name default  函数名字
     * @return
     */
    @Bean(name = "transportClient")
    public TransportClient transportClient() {
        //        // 1. start
        Settings settings = Settings.builder()
                .put("cluster.name", clusterName).put("node.name", nodeName).build();
        TransportClient transportClient = null;
        try {
            transportClient = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName(hostName), port));
        } catch (UnknownHostException e) {
            log.info("UnknownHostException", e);
        }
        return transportClient;
    }

}
