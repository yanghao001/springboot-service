package com.haozi.nettywebsocket.service;

import com.haozi.nettywebsocket.config.ProtocolConfig;
import com.haozi.nettywebsocket.handler.WebChannelInitializer;
import com.haozi.nettywebsocket.server.WebSocketServer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author hao.yang
 * @date 2019/7/10
 */
@Component
public class WebSocketService {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    public void handleFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {

        // 1. text frame
        if (frame instanceof TextWebSocketFrame) {
            String text = ((TextWebSocketFrame) frame).text();
            TextWebSocketFrame rspFrame = new TextWebSocketFrame(text);
            logger.info("recieve TextWebSocketFrame from channel {}", ctx.channel());
            // 发给其他所有channel
            for (Channel ch : WebChannelInitializer.channelMap.values()) {
                if (ctx.channel().equals(ch)) {
                    continue;
                }
                ch.writeAndFlush(rspFrame);
                logger.info("write text[{}] to channel {}", text, ch);
            }
            return;
        }

        // 2. ping frame, 回复pong frame即可
        if (frame instanceof PingWebSocketFrame) {
            logger.info("recieve PingWebSocketFrame from channel {}", ctx.channel());
            ctx.channel().writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
            return;
        }

        if (frame instanceof PongWebSocketFrame) {
            logger.info("recieve PongWebSocketFrame from channel {}", ctx.channel());
            return;
        }

        // 3. close frame,
        if (frame instanceof CloseWebSocketFrame) {
            logger.info("recieve CloseWebSocketFrame from channel {}", ctx.channel());
            WebSocketServerHandshaker handshaker = ctx.channel().attr(ProtocolConfig.ATTR_HANDSHAKER).get();
            if (handshaker == null) {
                logger.error("channel {} have no HandShaker", ctx.channel());
                return;
            }
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        logger.warn("unhandle binary frame from channel {}", ctx.channel());
    }

}
