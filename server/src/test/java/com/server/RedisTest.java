package com.server;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Author admin
 * @Date 2019/11/23 22:53
 * @Description 单元测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test1() {
        ValueOperations ops = redisTemplate.opsForValue();
        ops.set("springboot:hello", 1000);
        System.out.println(ops.get("springboot:hello"));

        ops.increment("springboot:hello", 500);
        System.out.println(ops.get("springboot:hello"));

    }

    @Test
    public void test2() {
        String key = "sb:list:1000";
        redisTemplate.delete(key);
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        List<String> list = Lists.newArrayList("a", "b", "c");
        listOperations.leftPush(key, "d");
        listOperations.leftPush(key, "e");
        listOperations.leftPushAll(key, list);

//        listOperations.set(key,0,"hello");

        listOperations.leftPop(key);

        System.out.println("当前元素{}：" + listOperations.range(key, 0, 15));

    }


    @Test
    public void test3() {
        String key = "sb:set:1000";
        String key2 = "sb:set:1001";

        redisTemplate.delete(key);
        redisTemplate.delete(key2);

        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        setOperations.add(key, "a", "b", "c");
        setOperations.add(key2, "b", "e", "f");

//        System.out.println(setOperations.members(key));
//        System.out.println(setOperations.randomMember(key));
        System.out.println(setOperations.randomMembers(key, 2));
    }

    @Test
    public void test5() {
        String key = "sb:zset";
        redisTemplate.delete(key);

      ZSetOperations<String,String> zSetOperations=  redisTemplate.opsForZSet();
      zSetOperations.add(key,"a",2);
      zSetOperations.add(key,"b",4);
      zSetOperations.add(key,"c",4);
      zSetOperations.add(key,"d",1);

        System.out.println(zSetOperations.size(key));
        System.out.println(zSetOperations.range(key,0,10));


    }
}
