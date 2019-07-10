package com.haozi.nettywebsocket.config;

import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.util.AttributeKey;

/**
 * @author hao.yang
 * @date 2019/7/10
 */
public class ProtocolConfig {

    public static final String HN_HTTP_CODEC = "HN_HTTP_CODEC";
    public static final String HN_HTTP_AGGREGATOR = "HN_HTTP_AGGREGATOR";
    public static final String HN_HTTP_CHUNK = "HN_HTTP_CHUNK";
    public static final String HN_SERVER = "HN_LOGIC";
    public static final int MAX_CONTENT_LENGTH = 65536;
    public static final String WEBSOCKET_UPGRADE = "websocket";
    public static final String WEBSOCKET_CONNECTION = "Upgrade";
    public static final String WEBSOCKET_URI_ROOT_PATTERN = "ws://%s:%d";

    /**
     *  handler key
     * */
    public static final AttributeKey<WebSocketServerHandshaker> ATTR_HANDSHAKER = AttributeKey.newInstance("ATTR_KEY_CHANNELID");

}
