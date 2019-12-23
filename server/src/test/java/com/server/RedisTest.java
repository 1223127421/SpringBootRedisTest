package com.server;

import com.google.common.collect.Maps;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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

    @Test
    public void stringTest() {
        //String

        String key = "redis:string";
        redisTemplate.delete(key);
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, "a");
        System.out.println("当前key对应的值：" + valueOperations.get(key));

        ValueOperations valueOperations2 = redisTemplate.opsForValue();
        valueOperations2.set(key, 100);
        valueOperations2.increment(key, 200);
        System.out.println("当前key对应的值：" + valueOperations2.get(key));

    }

    @Test
    public void listTest() {
        //list有序可以重复

        String key = "redis:list";
        redisTemplate.delete(key);

        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        listOperations.leftPush(key, "a");
        listOperations.leftPush(key, "b");
        listOperations.leftPush(key, "c");

        List<String> list = Lists.newArrayList("d", "e");
        listOperations.leftPushAll(key, list);
        System.out.println("当前列表元素个数：" + listOperations.size(key));

//        System.out.println("当前列表中下标为0的元素：" + listOperations.index(key, 0));
//        System.out.println("当前列表中下标为1的元素：" + listOperations.index(key, 1));

        //当前列表从右边弹出一个元素（弹出后会被删除）
//        String rightStr = listOperations.rightPop(key);
//        System.out.println("" + rightStr);

        //修改下标为0的元素
//        listOperations.set(key, 0, "admin");
//        System.out.println("当前列表中下标为0的元素：" + listOperations.index(key, 0));

        //删除下表为0，且值是admin的元素
//        listOperations.remove(key, 0, "admin");

        //获取list中所有元素
        List<String> result = listOperations.range(key, 0, listOperations.size(key));
        result.forEach(System.out::println);

    }

    @Test
    public void setTest() {
        //set无序且不可重复

        String key = "redis:list";
        redisTemplate.delete(key);

        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        setOperations.add(key, "a");
        setOperations.add(key, "b");
        setOperations.add(key, "c");
        setOperations.add(key, "c");

//        System.out.println("集合key中的元素个数：" + setOperations.size(key));

        //随机获取一个元素(不会删除)
        setOperations.randomMember(key);
        //随机获取n个元素
        List<String> nLists = setOperations.randomMembers(key, 2);

        //随机弹出一个元素（会被删除）
        setOperations.pop(key);
        //随机弹出n个元素（会被删除）
        setOperations.pop(key, 2);

        //删除集合中的元素"c"
        setOperations.remove(key, "c");

        //获取set中所有元素
        Set<String> result = setOperations.members(key);
        result.forEach(System.out::println);

        //差集 setOperations.difference(key1,key2)
        //交集 setOperations.intersect(key1,key2)
        //并集 setOperations.union(key1,key2)

    }

    @Test
    public void zsetTest() {
        //zset有序集合（有序且不可重复）

        String key = "redis:zset";
        redisTemplate.delete(key);

        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        //score可以重复，但是值不能重复
        //如果score相同，则先按照score排序，然后相同score对应的值再次排序
        //如果value重复，则score会替换成新的score
        zSetOperations.add(key, "a", 1);
        zSetOperations.add(key, "b2", 2);
        zSetOperations.add(key, "b1", 2);
        zSetOperations.add(key, "c", 5);
        zSetOperations.add(key, "a", 6);

        System.out.println("集合中的元素个数：" + zSetOperations.size(key));

//        System.out.println("获取元素a的分数（score）：" + zSetOperations.score(key, "a"));
//        System.out.println("正序中a的排名：" + zSetOperations.rank(key, "a") + 1);
//        System.out.println("倒序中a的排名：" + zSetOperations.reverseRank(key, "a") + 1);

        zSetOperations.incrementScore(key, "a", 5);
//        System.out.println("获取元素a的分数（score）：" + zSetOperations.score(key, "a"));

        // 删除元素a
        zSetOperations.remove(key, "a");

        //取出分数区间的元素
        Set<String> set1 = zSetOperations.rangeByScore(key, 0, 3);

        //正序获取所有元素
        Set<String> result = zSetOperations.range(key, 0, zSetOperations.size(key));
        //倒叙获取所有元素
        Set<String> result2 = zSetOperations.reverseRange(key, 0, zSetOperations.size(key));
//        result.forEach(System.out::println);

        //获取正序排序后的所有score和value
        Set<ZSetOperations.TypedTuple<String>> valueAndScore = zSetOperations.rangeWithScores(key, 0, zSetOperations.size(key));
        //获取倒序排序后的所有score和value
        Set<ZSetOperations.TypedTuple<String>> valueAndScore2 = zSetOperations.reverseRangeWithScores(key, 0, zSetOperations.size(key));
//        for (ZSetOperations.TypedTuple<String> tuples : valueAndScore) {
//            System.out.println("score:" + tuples.getScore() + ",value:" + tuples.getValue());
//        }
        valueAndScore.forEach(tuple -> {
            System.out.println("score:" + tuple.getScore() + ",value:" + tuple.getValue());
        });

    }

    @Test
    public void hashTest() {
        //hash

        String key = "redis:hash:user";
        redisTemplate.delete(key);

        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(key, "name", "admin");
        hashOperations.put(key, "age", "18");
        hashOperations.put(key, "id", "10001");

        Map<String, String> dataMap = Maps.newHashMap();
        dataMap.put("sex", "1");
        dataMap.put("birth", "20190101");
        hashOperations.putAll(key, dataMap);

        System.out.println("获取元素的个数:" + hashOperations.size(key));

//        System.out.println("获取name的value:" + hashOperations.get(key, "name"));

//        System.out.println("判断name是否存在："+hashOperations.hasKey(key,"name"));

        //获取所有hashKey
        Set<String> keySet = hashOperations.keys(key);
//        System.out.println("获取所有hashKey:" + keySet);

        //如果name存在，则不会修改value,如果name不存在，则添加新的value
        hashOperations.putIfAbsent(key, "name", "admin2");

        //h获取所有的元素
        Map<String, String> result = hashOperations.entries(key);
        Set<String> keys = result.keySet();
        for (String k : keys) {
            System.out.println("hashKey:" + k + ",value:" + result.get(k));
        }
    }

    @Test
    public void hyperLogLogTest() {

        HyperLogLogOperations<String, String> hyperLogLogOperations = redisTemplate.opsForHyperLogLog();

        GeoOperations<String, String> geoOperations = redisTemplate.opsForGeo();
    }

    @Test
    public void expireTest() throws Exception {
        //测试expire(失效时间)

        String key = "redis:expire";
        redisTemplate.delete(key);

        //设置过期时间（方式一）
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, "zhangsan", 5, TimeUnit.SECONDS);
        System.out.println("取出数据：" + valueOperations.get(key));
        Thread.sleep(5000);
        System.out.println("5秒后取出数据：" + valueOperations.get(key));

        //设置过期时间（方式二）
        valueOperations.set(key, "lisi");
        redisTemplate.expire(key, 5, TimeUnit.SECONDS);
        System.out.println("取出数据：" + valueOperations.get(key));
        Thread.sleep(5000);
        System.out.println("5秒后取出数据：" + valueOperations.get(key));

    }

}
