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

import java.util.ArrayList;
import java.util.List;

/**
 * @Author admin
 * @Date 2019/11/24 21:56
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
    public void addPro(Product product) {
        if (product == null) {
            return;
        }
        product.setId(null);
        productMapper.insertSelective(product);

        if (product.getId() > 0) {
            ListOperations<String, Product> operations = redisTemplate.opsForList();
            operations.leftPush(Constant.RedisListPrefix + product.getUserId(), product);
        }

    }

    public List<Product> getByUserId(Integer userId) {
        List<Product> list = new ArrayList<>();
        ListOperations<String, Product> listOperations = redisTemplate.opsForList();
        String key = Constant.RedisListPrefix + userId;
//        list = listOperations.range(key, 0, listOperations.size(key));
//        Collections.reverse(list);

        Product product = listOperations.rightPop(key);
        while (true) {
            if (product == null) {
                break;
            }
            list.add(product);
            product = listOperations.rightPop(key);
        }

        return list;
    }

    @Transactional(rollbackFor = Exception.class)
    public void putNotice(Notice notice) {
        if (notice == null) {
            return;
        }
        notice.setId(null);
        noticeMapper.insertSelective(notice);
        if (notice.getId() > 0) {
            //存到消息队列中
            //applicationEvent&Listener  Rabbitmq  jms
            ListOperations<String, Notice> listOperations = redisTemplate.opsForList();
            listOperations.leftPush(Constant.RedisListNoticePrefix, notice);
        }

    }
}
