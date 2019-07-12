package com.haozi.nio.server;

import com.haozi.nio.utils.ByteUtils;
import com.haozi.nio.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * NIO 多线程下文件下载（关键点在于selectionKey取消）
 *
 * @author hao.yang
 * @date 2019/7/11
 */
@Slf4j
@Component
public class NioSocketServer {

    private ExecutorService pool;

    @PostConstruct
    public void init() {
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        threadPool.execute(() -> server());
    }

    public void server() {
        try {
            pool = Executors.newFixedThreadPool(5);
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(8005));
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    try {
                        if (key.isAcceptable()) {
                            log.info("create upgrade channel...");
                            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                            SocketChannel sc = ssc.accept();
                            sc.configureBlocking(false);
                            SelectionKey k = sc.register(selector, SelectionKey.OP_READ);
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            k.attach(buffer);
                        } else if (key.isReadable()) {
                            key.interestOps(key.interestOps()&(~SelectionKey.OP_READ));
                            pool.execute(new Handler(key));
                        } else if (key.isWritable()) {
                            SocketChannel client = (SocketChannel) key.channel();
                            ByteBuffer buffer = (ByteBuffer) key.attachment();
                            while (buffer.hasRemaining()) {
                                if (client.write(buffer) == 0) {
                                    break;
                                }
                            }
                        }
                    } catch (IOException e) {
                        log.error("IOException", e);
                        key.cancel();
                        key.channel().close();
                    }
                }

            }

        } catch (IOException e) {
            log.info("IOException", e);
        }

    }

    public static class Handler implements Runnable {

        private SelectionKey key;

        public Handler(SelectionKey key) {
            this.key = key;
        }

        @Override
        public void run() {
            try {
                SocketChannel sc = (SocketChannel) key.channel();
                log.info("read data from channel");
                ByteBuffer buffer = (ByteBuffer) key.attachment();
                buffer.clear();
                int len = 0;
                while ((len = sc.read(buffer)) > 0) {//非阻塞，立刻读取缓冲区可用字节
                    byte[] data = buffer.array();
                    log.info("read data:{}", StringUtils.bytesToHexString(data));
                    handle(sc, null, data);
                    buffer.clear();
                }
                if (len == -1) {
                    log.info("client closed...");
                    sc.close();
                }
                //没有可用字节,继续监听OP_READ
                key.interestOps(key.interestOps() | SelectionKey.OP_READ);
                key.selector().wakeup();
            } catch (Exception e) {
                log.error("IOException", e);
                key.cancel();
                try {
                    key.channel().close();
                } catch (IOException e1) {
                    log.error("IOException", e1);
                }
            }
        }

        /**
         * 业务处理模块
         */
        public void handle(SocketChannel sc, byte[] readBytes, byte[] data) throws IOException {
            log.info("业务处理");
            byte[] upgradeData = readFile();
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
            } catch (IOException e) {
                log.info("IOExeception", e);
            }
            return data;
        }
    }

}

