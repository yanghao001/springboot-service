package com.haozi.nio;

import com.haozi.nio.utils.ByteUtils;
import com.haozi.nio.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class NioApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void client() {
        try {
            int i = 0;
            ByteBuffer buffer;
            //连接服务端socket
            SocketChannel socketChannel = SocketChannel.open();
            SocketAddress socketAddress = new InetSocketAddress("localhost", 8005);
            socketChannel.connect(socketAddress);

            while (i < 2) {
                String req = "6810001b74000000000000000000008f4530303738443330383631343530300000000000a004e6da000000004c4b462b58656b4142774975644a506b7548687161654c732f6a38504d6165445875436a3876366778357465344b50792f7144486d3137676f2f4c2b6f4d6562336d4e73306149392b596d70314158653678316132413d3d";
                buffer = ByteBuffer.wrap(StringUtils.hexStringToBytes(req));
                byte[] by = StringUtils.hexStringToBytes(buffer.toString());
                socketChannel.write(buffer);
                buffer.flip();
                log.info("向服务端发送消息2: " + StringUtils.bytesToHex(ByteUtils.decodeValue(buffer)));
                buffer.clear();

                //从服务端读取消息
                int readLenth = socketChannel.read(buffer);
                buffer.flip();
                byte[] bytes = new byte[readLenth];
                buffer.get(bytes);
                System.out.println(new String(bytes, "UTF-8"));
                buffer.clear();
                log.info("接收服务端消息" + StringUtils.bytesToHexString(bytes));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            log.info("IOException", e);
        }
    }

}
