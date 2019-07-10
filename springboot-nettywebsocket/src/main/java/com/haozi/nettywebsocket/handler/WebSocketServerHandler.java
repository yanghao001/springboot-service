package com.haozi.nettywebsocket.handler;

import com.haozi.nettywebsocket.service.HttpService;
import com.haozi.nettywebsocket.service.WebSocketService;
import com.haozi.nettywebsocket.utils.SpringBeanUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author hao.yang
 * @date 2019/7/10
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

    private WebSocketService websocketService = SpringBeanUtil.getBean(WebSocketService.class);

    private HttpService httpService =SpringBeanUtil.getBean(HttpService.class);

    public WebSocketServerHandler() {
        super();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof FullHttpRequest) {
            httpService.handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            websocketService.handleFrame(ctx, (WebSocketFrame) msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

}
