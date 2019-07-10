package com.haozi.netty.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author hao.yang
 * @date 2019/7/9
 */
@Slf4j
public class DeviceChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel sc) {
        ChannelPipeline pipeline = sc.pipeline();
        pipeline.addLast(new Decoder()); // 编码器
        pipeline.addLast(new Encoder()); // 解码器
        pipeline.addLast(new DeviceDataPacketHandler()); // 业务处理模块
        log.info("模块绑定通道");
    }
}
