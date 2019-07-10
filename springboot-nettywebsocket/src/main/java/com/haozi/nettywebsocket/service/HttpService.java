package com.haozi.nettywebsocket.service;

import com.haozi.nettywebsocket.config.ProtocolConfig;
import com.haozi.nettywebsocket.server.WebSocketServer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * webSocket http三次握手
 * @author hao.yang
 * @date 2019/7/10
 *
 */
@Component
public class HttpService {
    private static final Logger logger = LoggerFactory.getLogger(HttpService.class);

    public void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {

        // 1. 该请求是不是websocket upgrade请求
        if (isWebSocketUpgrade(req)) {
            logger.info("upgrade to websocket protocol");

            String subProtocols = req.headers().get(HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL);
            WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory(WebSocketServer.WEBSOCKET_URI_ROOT, subProtocols, false);
            WebSocketServerHandshaker handshaker = factory.newHandshaker(req);

            // 2. 请求头不合法, 导致handshaker没创建成功
            if (handshaker == null) {
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            } else {
                // 响应该请求
                handshaker.handshake(ctx.channel(), req);
                // 把handshaker 绑定给Channel, 以便后面关闭连接用
                ctx.channel().attr(ProtocolConfig.ATTR_HANDSHAKER).set(handshaker);// attach handshaker to this channel
            }
            return;
        }
        logger.info("ignoring normal http request");
    }

    /**
     *
     * 三者与：1.GET? 2.Upgrade头 包含websocket字符串?  3.Connection头 包含 Upgrade字符串?
     *
     */
    private boolean isWebSocketUpgrade(FullHttpRequest req) {
        HttpHeaders headers = req.headers();
        return req.method().equals(HttpMethod.GET)
                && headers.get(HttpHeaderNames.UPGRADE).contains(ProtocolConfig.WEBSOCKET_UPGRADE)
                && headers.get(HttpHeaderNames.CONNECTION).contains(ProtocolConfig.WEBSOCKET_CONNECTION);
    }
}
