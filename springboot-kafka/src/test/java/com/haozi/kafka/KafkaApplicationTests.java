package com.haozi.kafka;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void testTime() throws ParseException {
        String time = "2019-11-02 08:38:57";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int timestamp = (int) sdf.parse(time).getTime();
        System.out.println("timestamp:" + timestamp);
    }

}
