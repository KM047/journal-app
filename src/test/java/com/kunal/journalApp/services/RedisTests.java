package com.kunal.journalApp.services;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
@Slf4j
public class RedisTests {


    @Autowired
    private RedisTemplate redisTemplate;

    @Disabled
    @Test
    void redisTesting() {

        try {
            redisTemplate.opsForValue().set("testKey", "testValue");
            Object testedValue = redisTemplate.opsForValue().get("salary");


            Assertions.assertNotNull(testedValue);
            int a= 1;
            log.info("The value is -> {}", testedValue);
        } catch (Exception e) {
            e.printStackTrace();

            log.error("Error while testing redis", e);
        }
    }
}
