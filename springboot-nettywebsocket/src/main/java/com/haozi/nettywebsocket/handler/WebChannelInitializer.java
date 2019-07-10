package com.haozi.nettywebsocket.handler;

import com.haozi.nettywebsocket.config.ProtocolConfig;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hao.yang
 * @date 2019/7/10
 */
public class WebChannelInitializer extends ChannelInitializer<SocketChannel> {

    private static final Logger logger = LoggerFactory.getLogger(WebChannelInitializer.class);

    /**
     * 保存所有WebSocket连接
     */
    public static Map<ChannelId, Channel> channelMap = new ConcurrentHashMap<ChannelId, Channel>();

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pl = ch.pipeline();
        pl.addLast(ProtocolConfig.HN_HTTP_CODEC, new HttpServerCodec());
        pl.addLast(ProtocolConfig.HN_HTTP_AGGREGATOR, new HttpObjectAggregator(ProtocolConfig.MAX_CONTENT_LENGTH));
        pl.addLast(ProtocolConfig.HN_HTTP_CHUNK, new ChunkedWriteHandler());
        pl.addLast(ProtocolConfig.HN_SERVER, new WebSocketServerHandler());

        channelMap.put(ch.id(), ch);
        logger.info("new channel {}", ch);
        ch.closeFuture().addListener((ChannelFutureListener) future -> {
            logger.info("channel close {}", future.channel());
            channelMap.remove(future.channel().id());
        });
    }

}
