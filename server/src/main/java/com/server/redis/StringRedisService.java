package com.server.redis;

import com.server.enums.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

/**
 * @Author admin
 * @Date 2019/11/24 0:00
 * @Description
 */
@Service
public class StringRedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public ValueOperations getOperation() {
        return stringRedisTemplate.opsForValue();
    }

    //判断redis中是否存在key
    public Boolean exists(String key) throws Exception {
        return stringRedisTemplate.hasKey(Constant.RedisStringPrefix + key);
    }

    public void put(String key, String value) {
        getOperation().set(Constant.RedisStringPrefix + key, value);
    }

    public Object get(String key) {
        return getOperation().get(Constant.RedisStringPrefix + key);
    }
}
