package com.haozi.iodemo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class IodemoApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void IOClient() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("localhost", 8081);
                    while (true) {
                        socket.getOutputStream().write((new Date() + ": hello world").getBytes()); //  socket.getChannel().write();
                        socket.getOutputStream().flush();
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            log.info("InterruptedException", e);
                        }
                    }
                } catch (IOException e) {
                    log.info("IOException", e);
                }
            }
        }).start();
    }

}
