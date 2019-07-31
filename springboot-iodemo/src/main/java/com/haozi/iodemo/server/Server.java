package com.haozi.iodemo.server;

import com.haozi.iodemo.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author hao.yang
 * @date 2019/7/31
 */
@Slf4j
public class Server implements Runnable {

    /**
     * Server服务端首先创建ServerSocket监听8081端口,
     * 然后创建线程不断调用阻塞方法 serversocket.accept()获取新的连接,
     * 当获取到新的连接给每条连接创建新的线程负责从该连接中读取数据,
     * 然后读取数据是以字节流的方式
     */
    @Override
    public void run() {
        try {
            log.info("init server success");
            ServerSocket serverSocket = new ServerSocket(8081);

            try {
                // 1. 阻塞方式获取新的连接
                Socket socket = serverSocket.accept();

                // 2. 创建新线程获取数据
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            byte[] readBytes = new byte[50];
                            InputStream inputStream = socket.getInputStream();
                            while (true) {
                                // 3. 将数据写入readBytes
                                inputStream.read(readBytes);
                                log.info("写入数据为：{}", StringUtils.bytesToHexString(readBytes));

                                if (-1 == inputStream.read(readBytes)) {
                                    log.info("client closed...");
                                    socket.close();
                                }
                            }
                        } catch (IOException e) {
                            log.info("IOException", e);
                        }
                    }
                }).start();
            } catch (IOException e) {
                log.info("IOException", e);
            }
        } catch (IOException e) {
            log.info("IOException", e);
        }

    }
}
