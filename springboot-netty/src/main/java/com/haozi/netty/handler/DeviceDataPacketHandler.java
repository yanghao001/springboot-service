package com.haozi.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.net.InetSocketAddress;

/**
 * @author hao.yang
 * @date 2019/7/9
 */
@Slf4j
public class DeviceDataPacketHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("通道连接成功...");
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.info("通道失去连接...");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.info("通道异常捕获...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.info("通道数据读入...");
    }


}
