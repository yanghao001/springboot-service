package com.haozi.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hao.yang
 * @date 2019/7/10
 */
@Slf4j
public class Encoder extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object ob, ByteBuf out) {  // ob可封装成输入对象
        log.info("对数据编码");
    }
}
