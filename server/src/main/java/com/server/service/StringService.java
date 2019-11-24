package com.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.entity.Item;
import com.model.mapper.ItemMapper;
import com.server.controller.StringController;
import com.server.redis.StringRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @Author admin
 * @Date 2019/11/23 23:46
 * @Description
 */
@Service
public class StringService {

    private static final Logger log = LoggerFactory.getLogger(StringController.class);

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StringRedisService stringRedisService;

    //如果在添加到缓存时失败，则回滚，保证缓存和数据库双写的一致性
    @Transactional(rollbackFor = Exception.class)
    public Integer addItem(Item item) throws Exception {
        item.setCreateTime(new Date());
        item.setId(null);
        itemMapper.insertSelective(item);

        Integer id = item.getId();
        //保证缓存和数据库双写的一致性，如果数据库添加失败，则不添加到缓存中
        if (id > 0) {
            //前缀+商品id作为key
            //序列化
            stringRedisService.put(id.toString(), objectMapper.writeValueAsString(item));
        }
        return id;
    }

    public Item getById(Integer id) throws Exception {
        Item item = null;
        if (id == null) {
            return null;
        }
        //判断key是否存在，如果key存在，则直接从redis中获取数据
        if (stringRedisService.exists(id.toString())) {
            String result = stringRedisService.get(id.toString()).toString();
            log.info("redis中取出的value:" + result);
            if (!StringUtils.isEmpty(result)) {
                //反序列化
                item = objectMapper.readValue(result, Item.class);
            }
        } else {
            //如果key不存在，说明redis中没有数据，则先从mysql中查询出来，然后在放入redis中
            item = itemMapper.selectByPrimaryKey(id);
            if (item != null) {
                stringRedisService.put(id.toString(), objectMapper.writeValueAsString(item));
            }
            log.info("先从mysql中查询出来，然后在放入redis中", item);
        }
        return item;
    }

}
