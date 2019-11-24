package com.server.service;

import com.model.entity.Product;
import com.model.mapper.ProductMapper;
import com.server.enums.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
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
}
