package com.haozi.nio.client;

import com.haozi.nio.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hao.yang
 * @date 2019/7/11
 */
@Slf4j
@Component
public class NioSocketServer {

    private static ExecutorService executorService = Executors.newFixedThreadPool(3);

    private static byte[] upgradeData;

    @PostConstruct
    public void init() {
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        threadPool.execute(() -> server());
    }

    public void server() {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(8005));
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey s = iterator.next();
                    if (s.isAcceptable()) {
                        log.info("create upgrade channel...");
                        ServerSocketChannel ssc = (ServerSocketChannel) s.channel();
                        SocketChannel sc = ssc.accept();
                        sc.configureBlocking(false);
                        sc.register(selector, SelectionKey.OP_READ);
                    }

                    executorService.execute(() -> {
                        log.info("enter into upgrade thread");
                        try {
                            if (s.isReadable()) {
                                log.info("read data from channel");
                                SocketChannel sc = (SocketChannel) s.channel();
                                ByteBuffer buffer = ByteBuffer.allocate(1024);
                                long bytesRead = sc.read(buffer);
                                if (bytesRead > 0) {
                                    byte[] data = new byte[buffer.position()];
                                    buffer.flip();
                                    buffer.get(data);
                                    log.info("read data:{}", StringUtils.bytesToHexString(data));
                                    handle(sc, upgradeData, data);
                                }

                                if (bytesRead == -1) {
                                    sc.close();
                                }
                            }
                        } catch (IOException e) {
                            log.error("IOException", e);
                        }
                    });
                    iterator.remove();
                }
            }

        } catch (IOException e) {
            log.info("IOException", e);
        }
    }

    public void handle(SocketChannel sc, byte[] readBytes, byte[] data) {
        log.info("业务处理");
    }

}

