package com.haozi.nio.server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * serverSelector负责轮询是否有新的连接,clientSelector负责轮询连接是否有数据可读.
 * 服务端监测到新的连接不再创建一个新的线程,而是直接将新连接绑定到clientSelector上,这样不用IO模型中1w个while循环在死等
 * clientSelector被一个while死循环包裹,如果在某一时刻有多条连接有数据可读通过 clientSelector.select(1)方法轮询出来进而批量处理
 * 数据的读写以内存块为单位
 *
 * @author hao.yang
 * @date 2019/7/31
 */
@Slf4j
public class NioServer implements Runnable {

    private static ExecutorService pool;

    @Override
    public void run() {
        log.info("init NioServer success");
        try {
            pool = Executors.newFixedThreadPool(1);
            Selector serverSelector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(8082));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(serverSelector, SelectionKey.OP_ACCEPT);  // 绑定serverSelector

            while (true) {
                serverSelector.select();
                Iterator<SelectionKey> iterator = serverSelector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    ServerSocketChannel ssc = null;
                    try {
                        if (key.isAcceptable()) {
                            log.info("create upgrade channel...");
                            ssc = (ServerSocketChannel) key.channel();
                            SocketChannel sc = ssc.accept();
                            sc.configureBlocking(false);
                            SelectionKey k = sc.register(serverSelector, SelectionKey.OP_READ);
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            k.attach(buffer);
                        }

                        if (key.isReadable()) {
                            Channel channel = key.channel(); // 用于绑定session
                            key.interestOps(key.interestOps() & (~SelectionKey.OP_READ));
                            pool.execute(() -> {
                                try {
                                    SocketChannel sc = (SocketChannel) key.channel();
                                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                                    buffer.clear();
                                    int len = 0;
                                    while ((len = sc.read(buffer)) > 0) {//非阻塞，立刻读取缓冲区可用字节
                                        byte[] data = buffer.array();
                                        // handle
                                        buffer.clear();
                                    }
                                    if (len == -1) {
                                        log.info("client closed...");
                                        sc.close();
                                    }
                                } catch (IOException e) {
                                    log.info("IOException", e);
                                }
                            });
                        }
                    } catch (IOException e) {
                        log.info("IOException", e);
                    }
                }
            }
        } catch (IOException e) {
            log.info("IOException", e);
        }
    }
}
