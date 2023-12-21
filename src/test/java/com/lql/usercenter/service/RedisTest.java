package com.lql.usercenter.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;

@SpringBootTest
public class RedisTest {

    @Resource
    private RedisTemplate redisTemplate;
    @Test
    public void test(){
        ValueOperations valueOperations= redisTemplate.opsForValue();
        valueOperations.set("testString","sakura");
        valueOperations.set("testInteger",10);
        System.out.println(valueOperations.get("testString"));
    }

}
