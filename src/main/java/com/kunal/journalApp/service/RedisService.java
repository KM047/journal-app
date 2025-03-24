package com.kunal.journalApp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T get(String  key, Class<T> entityClass) {

        try {
            Object obj = redisTemplate.opsForValue().get(key);
            ObjectMapper mapper = new ObjectMapper();
            if (obj == null) {
                return null;
            }
            return mapper.readValue(obj.toString(), entityClass);
        } catch (Exception e) {
            log.error("Error while getting data from redis", e);
            return null;
        }

    }


    public void set(String key, Object value, Long ttl) {

        try {

            if (key != null && value != null) {

                ObjectMapper objectMapper = new ObjectMapper();

                String jsonValue = objectMapper.writeValueAsString(value);

                redisTemplate.opsForValue().set(key, jsonValue, ttl, TimeUnit.SECONDS);
            }


        } catch (Exception e) {
            log.error("Error while getting data from redis", e);
        }

    }

}
