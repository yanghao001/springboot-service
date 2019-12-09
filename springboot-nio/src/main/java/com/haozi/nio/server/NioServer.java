package com.haozi.nio.server;

import com.haozi.nio.utils.ByteUtils;
import com.haozi.nio.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ＮＩＯ　单线程
 * @author hao.yang
 * @date 2019/7/11
 */
@Slf4j
@Component
public class NioServer {

    private static ExecutorService executorService = Executors.newFixedThreadPool(1);

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
                    iterator.remove();
                    try {
                        if (s.isAcceptable()) {
                            log.info("create upgrade channel...");
                            ServerSocketChannel ssc = (ServerSocketChannel) s.channel();
                            SocketChannel sc = ssc.accept();
                            sc.configureBlocking(false);
                            sc.register(selector, SelectionKey.OP_READ);
                        }

                        if (s.isReadable()) {
                            SocketChannel sc = (SocketChannel) s.channel();
                            try {
                                log.info("read data from channel");
                                ByteBuffer buffer = ByteBuffer.allocate(1024);
                                long bytesRead = sc.read(buffer); // position位置0读入
                                if (bytesRead > 0) {
                                    byte[] data = new byte[buffer.position()];
                                    buffer.flip(); // position位于最后位置，此操作将position置0
                                    buffer.get(data); // 将缓冲区数据写入data
                                    log.info("read data:{}", StringUtils.bytesToHexString(data));
                                    handle(sc, upgradeData, data);
                                }

                                if (bytesRead == -1) {
                                    sc.close();
                                }
                            } catch (IOException e) {
                                sc.close();
                                log.error("IOException", e);
                            }
                        }
                    } catch (IOException e) {
                        s.cancel();
                        log.error("IOException", e);
                    }
                }
            }

        } catch (IOException e) {
            log.error("IOException", e);
        }

    }

    /**
     * 业务处理模块
     */
    public void handle(SocketChannel sc, byte[] readBytes, byte[] data) throws IOException {
        log.info("业务处理");
        upgradeData = readFile();
        ByteBuffer out = ByteBuffer.wrap(upgradeData);
        int len = sc.write(out);
        if (len != 0) {
            log.info("write bytes len={}", len);
        }
        log.info("upgrade[RESP]{}", StringUtils.bytesToHexString(upgradeData));

    }

    /**
     * 读入文件内容
     */
    public byte[] readFile() {
        byte[] data = null;
        try {
            ByteBuffer buffer = ByteBuffer.allocate(100 * 1024);
            String localPath = "D:\\file\\f1_ucosii.bin";
            FileInputStream fis = new FileInputStream(localPath);
            FileChannel fileChannel = fis.getChannel();
            int count = fileChannel.read(buffer);
            if (count <= 0) {
                fileChannel.close();
            }
            buffer.flip();
            data = ByteUtils.decodeValue(buffer);
            log.info("load file:{}", StringUtils.bytesToHex(data));
            buffer.clear();
            fis.close();
        } catch (IOException e) {
            log.info("IOExeception", e);
        }
        return data;
    }

}

