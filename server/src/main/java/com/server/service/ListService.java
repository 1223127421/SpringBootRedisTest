package com.server.service;

import com.model.entity.Notice;
import com.model.entity.Product;
import com.model.mapper.NoticeMapper;
import com.model.mapper.ProductMapper;
import com.server.constants.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * @Description
 */
@Service
public class ListService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Transactional(rollbackFor = Exception.class)
    public void add(Product product) {
        product.setId(null);
        productMapper.insertSelective(product);
        if (product.getId() != null) {
            ListOperations<String, Product> listOperations = redisTemplate.opsForList();
            String key = Constant.RedisListProductPrefix + product.getUserId();
            listOperations.leftPush(key, product);
        }
    }

    public List<Product> getByUserId(Integer userId) {
        List<Product> list = null;
        String key = Constant.RedisListProductPrefix + userId;
        ListOperations<String, Product> listOperations = redisTemplate.opsForList();
        if (redisTemplate.hasKey(key)) {
            //倒叙
            list = listOperations.range(key, 0, listOperations.size(key));
//            Collections.reverse(list);
        } else {
            list = productMapper.listProductsByUId(userId);
            if (list != null) {
                listOperations.leftPushAll(key, list);
            }
        }
        return list;
    }

    @Transactional(rollbackFor = Exception.class)
    public void addNotice(Notice notice) {
        notice.setId(null);
        noticeMapper.insertSelective(notice);
        if (notice.getId() != null) {
            // 塞入List列表中(队列)，准备被拉取异步通知至不同的商户的邮箱 -
            // applicationEvent&Listener;Rabbitmq;jms
            ListOperations<String, Notice> listOperations = redisTemplate.opsForList();
            String key = Constant.RedisListNoticePrefix;
            listOperations.leftPush(key, notice);
        }
    }

}
