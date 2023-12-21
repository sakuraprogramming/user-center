package com.lql.usercenter.service;

import org.junit.jupiter.api.Test;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class RedissonTest {

    @Resource
    private RedissonClient redissonClient;

    @Test
    public void test() {
        // 列表
        RList<String> rList = redissonClient.getList("test-list");
        rList.add("sakura");
        System.out.println(rList.get(0));
        rList.remove(0);


    }

}
