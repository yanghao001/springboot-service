package com.haozi.nettywebsocket.server;


import com.haozi.nettywebsocket.config.ProtocolConfig;
import com.haozi.nettywebsocket.config.ServerConfig;
import com.haozi.nettywebsocket.handler.WebChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author hao.yang
 * @date 2019/7/10
 */
@Component
public class WebSocketServer {
    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    public static String WEBSOCKET_URI_ROOT;

    @Autowired
    private ServerConfig serverConfig;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    private ServerBootstrap bootstrap;

    public void start() {
        WEBSOCKET_URI_ROOT = String.format(ProtocolConfig.WEBSOCKET_URI_ROOT_PATTERN, serverConfig.getHost(), serverConfig.getPort());
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new WebChannelInitializer());
    }

    @PostConstruct
    public void run() {
        start();
        try {
            ChannelFuture future = bootstrap.bind(serverConfig.getPort()).addListener((ChannelFutureListener) future1 -> {
                if (future1.isSuccess()) {
                    log.info("websocket started. and listen port:{}", serverConfig.getPort());
                }
            }).sync();

            future.channel().closeFuture().addListener((ChannelFutureListener) future12 ->
                    log.info("server channel {} closed.", future12.channel())
            ).sync();
        } catch (InterruptedException e) {
            log.error("InterruptedException", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
        log.info("websocket server shutdown");
    }
}