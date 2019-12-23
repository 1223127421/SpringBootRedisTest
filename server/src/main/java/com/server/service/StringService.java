package com.server.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.model.entity.Item;
import com.model.mapper.ItemMapper;
import com.server.constants.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Description
 */
@Service
public class StringService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Transactional(rollbackFor = Exception.class)
    public void add(Item item) {
        item.setId(null);
        item.setCreateTime(new Date());
        itemMapper.insertSelective(item);
        //保证缓存和数据库的双写一致性
        if (item.getId() > 0) {
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            String key = Constant.RedisStrinItemPrefix + item.getId().toString();
            valueOperations.set(key, JSON.toJSONString(item));
        }
    }

    //先从缓存中查询，如果插不到，则再从数据库查询，并把查到的数据再次放入缓存中
    public Item getById(Integer id) throws Exception {
        Item item = null;
        String key = Constant.RedisStrinItemPrefix + id;
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(key)) {
            item = JSON.parseObject(valueOperations.get(key), new TypeReference<Item>() {
            });
        } else {
            item = itemMapper.selectByPrimaryKey(id);
            if (item != null) {
                valueOperations.set(key, JSON.toJSONString(item));
            }
        }
        return item;

    }

    @Transactional(rollbackFor = Exception.class)
    public void del(Integer id) {
        Integer result = itemMapper.deleteByPrimaryKey(id);
        if (result > 0) {
            redisTemplate.delete(Constant.RedisStrinItemPrefix + id.toString());
        }
    }
}
