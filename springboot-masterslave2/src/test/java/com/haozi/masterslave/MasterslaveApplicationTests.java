package com.haozi.masterslave;

import com.haozi.masterslave.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MasterslaveApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testQuery() {
        System.out.println("查询到用户总数为=" + userRepository.queryCount());
    }

    @Test
    public void testInsert() {
        userRepository.save();
        System.out.println("写入数据");
    }
}
